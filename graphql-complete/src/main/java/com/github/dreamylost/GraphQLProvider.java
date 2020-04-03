package com.github.dreamylost;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dreamylost.Instrumentation.CustomInstrumentation;
import com.github.dreamylost.Instrumentation.FieldValidationBuilder;
import com.github.dreamylost.context.Context;
import com.github.dreamylost.context.ContextProvider;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.execution.instrumentation.ChainedInstrumentation;
import graphql.execution.instrumentation.Instrumentation;
import graphql.execution.instrumentation.dataloader.DataLoaderDispatcherInstrumentation;
import graphql.execution.instrumentation.tracing.TracingInstrumentation;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.dataloader.DataLoaderRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Map;

import static graphql.execution.instrumentation.dataloader.DataLoaderDispatcherInstrumentationOptions.newOptions;
import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;
import static java.util.Arrays.asList;

/**
 * 提供graphql能力
 *
 * @author 梦境迷离
 * @time 2020年03月24日17:57:08
 */
@Component
public class GraphQLProvider {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DataLoaderRegistry dataLoaderRegistry;

    @Autowired
    private StarWarsWiring starWarsWiring;

    @Autowired
    private ContextProvider contextProvider;

    private GraphQL graphQL;

    //作为bean，使用该bean执行gql请求
    @Bean
    public GraphQL graphQL() {
        return graphQL;
    }

    //初始化gql
    @PostConstruct
    public void init() throws IOException {
        //初始化读取gql schema文件
        URL url = Resources.getResource("starWarsSchemaAnnotated.graphqls");
        String sdl = Resources.toString(url, Charsets.UTF_8);
        GraphQLSchema graphQLSchema = buildSchema(sdl);
        //本示例使用DataLoader技术来确保最有效地加载数据，通过graphql上下文对象将其传递给数据获取程序
        //DataLoaderDispatcherInstrumentation也是一种Instrumentation，用来分发dataloader
        DataLoaderDispatcherInstrumentation dlInstrumentation = new DataLoaderDispatcherInstrumentation(dataLoaderRegistry, newOptions().includeStatistics(true));
        //ChainedInstrumentation可以组合多个仪器，TracingInstrumentation是追踪，FieldValidationBuilder是字段验证
        //CustomInstrumentation是自定义的仪器
        Instrumentation instrumentation = new ChainedInstrumentation(asList(FieldValidationBuilder.builder(), new TracingInstrumentation(), new CustomInstrumentation(), dlInstrumentation));
        this.graphQL = GraphQL.newGraphQL(graphQLSchema).instrumentation(instrumentation).build();
    }

    //执行gql
    void executeGraphqlQuery(HttpServletResponse httpServletResponse, String operationName, String query, Map<String, Object> variables) throws IOException {
        //授权 执行请求
        Context context = contextProvider.newContext();

        ExecutionInput executionInput = ExecutionInput.newExecutionInput()
                .query(query)
                .variables(variables)
                .operationName(operationName)
                .context(context)
                .build();

        //executeAsync执行返回promise
        ExecutionResult executionResult = graphQL.execute(executionInput);
        handleNormalResponse(httpServletResponse, executionResult);
    }

    private void handleNormalResponse(HttpServletResponse httpServletResponse, ExecutionResult executionResult) throws IOException {
        Map<String, Object> result = executionResult.toSpecification();
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        //gql一般返回json
        String body = objectMapper.writeValueAsString(result);
        PrintWriter writer = httpServletResponse.getWriter();
        writer.write(body);
        writer.close();
    }

    //加载解析schema文件
    private GraphQLSchema buildSchema(String sdl) {
        //读取从resources下加载解析的gql schema文件的字符串
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
        //创建运行时类型织入
        RuntimeWiring.Builder builder = RuntimeWiring.newRuntimeWiring();
        //动态映射
        //运行时织入包含DataFetcher、TypeResolvers和自定义Scalar，它们是制作完全可执行的schema所必需的。
        builder.type(newTypeWiring("Query")
                .dataFetcher("hero", starWarsWiring.heroDataFetcher)
                .dataFetcher("human", starWarsWiring.humanDataFetcher)
                .dataFetcher("humans", starWarsWiring.humansDataFetcher)
                .dataFetcher("droid", starWarsWiring.droidDataFetcher));
        builder.type(newTypeWiring("Human").dataFetcher("friends", starWarsWiring.friendsDataFetcher));
        builder.type(newTypeWiring("Droid").dataFetcher("friends", starWarsWiring.friendsDataFetcher));
        builder.type(newTypeWiring("Character").typeResolver(starWarsWiring.characterTypeResolver));
        builder.type(newTypeWiring("Episode").enumValues(starWarsWiring.episodeResolver));
        builder.scalar(starWarsWiring.Email);
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        //创建gql schema对象，构造可执行请求的gql
        return schemaGenerator.makeExecutableSchema(typeRegistry, builder.build());
    }

}
