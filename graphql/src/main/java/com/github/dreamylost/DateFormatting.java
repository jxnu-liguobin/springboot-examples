package com.github.dreamylost;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.Scalars;
import graphql.schema.*;
import graphql.schema.idl.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * 使用指令 格式化日期
 *
 * @author 梦境迷离
 * @time 2020年04月08日
 */
public class DateFormatting implements SchemaDirectiveWiring {
    @Override
    public GraphQLFieldDefinition onField(SchemaDirectiveWiringEnvironment<GraphQLFieldDefinition> environment) {
        GraphQLFieldDefinition field = environment.getElement();
        GraphQLFieldsContainer parentType = environment.getFieldsContainer();
        // DataFetcherFactories.wrapDataFetcher是包装数据读取器的助手，以便正确处理CompletionStage和POJO
        DataFetcher originalFetcher = environment.getCodeRegistry().getDataFetcher(parentType, field);
        DataFetcher dataFetcher = DataFetcherFactories.wrapDataFetcher(originalFetcher, ((dataFetchingEnvironment, value) -> {
            DateTimeFormatter dateTimeFormatter = buildFormatter(dataFetchingEnvironment.getArgument("format"));
            if (value instanceof LocalDateTime) {
                return dateTimeFormatter.format((LocalDateTime) value);
            }
            return value;
        }));

        // 这将通过为日期格式添加一个新的“format”参数来扩展字段，它允许客户端进行选择，并包装基本数据获取器，以便在基本值之上执行格式化。
        FieldCoordinates coordinates = FieldCoordinates.coordinates(parentType, field);
        environment.getCodeRegistry().dataFetcher(coordinates, dataFetcher);

        return field.transform(builder -> builder
                .argument(GraphQLArgument
                        .newArgument()
                        .name("format")
                        .type(Scalars.GraphQLString)
                        .defaultValue("dd-MM-YYYY")
                )
        );
    }

    private DateTimeFormatter buildFormatter(String format) {
        String dtFormat = format != null ? format : "dd-MM-YYYY";
        return DateTimeFormatter.ofPattern(dtFormat);
    }


    static GraphQLSchema buildSchema() {

        String sdlSpec = "directive @dateFormat on FIELD_DEFINITION\n" +
                "type Query {\n" +
                "    dateField : String @dateFormat \n" +
                "}";

        TypeDefinitionRegistry registry = new SchemaParser().parse(sdlSpec);

        RuntimeWiring runtimeWiring = RuntimeWiring.newRuntimeWiring()
                .directive("dateFormat", new DateFormatting())
                .build();

        return new SchemaGenerator().makeExecutableSchema(registry, runtimeWiring);
    }

    public static void main(String[] args) {
        GraphQLSchema schema = buildSchema();
        GraphQL graphql = GraphQL.newGraphQL(schema).build();

        Map<String, Object> root = new HashMap<>();
        root.put("dateField", LocalDateTime.of(1969, 10, 8, 0, 0));

        String query = "" +
                "query {\n" +
                "    default : dateField \n" +
                "    usa : dateField(format : \"MM-dd-YYYY\") \n" +
                "}";

        ExecutionInput executionInput = ExecutionInput.newExecutionInput()
                .root(root)
                .query(query)
                .build();

        ExecutionResult executionResult = graphql.execute(executionInput);
        Map<String, Object> data = executionResult.getData();
        for (String kv : data.keySet()) {
            System.out.println("k=" + kv + ", v=" + data.get(kv));
        }

        // data['default'] == '08-10-1969'
        // data['usa'] == '10-08-1969'
    }
}