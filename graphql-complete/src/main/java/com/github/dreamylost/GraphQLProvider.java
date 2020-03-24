package com.github.dreamylost;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
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
import java.io.IOException;
import java.net.URL;

import static graphql.execution.instrumentation.dataloader.DataLoaderDispatcherInstrumentationOptions.newOptions;
import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;
import static java.util.Arrays.asList;

/**
 * 处理/graphql请求
 *
 * @author 梦境迷离
 * @time 2020年03月24日17:57:08
 */
@Component
public class GraphQLProvider {
    private GraphQL graphQL;
    private DataLoaderRegistry dataLoaderRegistry;
    private StarWarsWiring starWarsWiring;

    @Autowired
    public GraphQLProvider(DataLoaderRegistry dataLoaderRegistry, StarWarsWiring starWarsWiring) {
        this.dataLoaderRegistry = dataLoaderRegistry;
        this.starWarsWiring = starWarsWiring;
    }

    @PostConstruct
    public void init() throws IOException {
        //读取资源文件
        URL url = Resources.getResource("starWarsSchemaAnnotated.graphqls");
        String sdl = Resources.toString(url, Charsets.UTF_8);
        GraphQLSchema graphQLSchema = buildSchema(sdl);

        //本示例使用DataLoader技术来确保最有效地加载数据（在本例中为StarWars字符）。 我们通过graphql上下文对象将其传递给数据获取程序
        DataLoaderDispatcherInstrumentation dlInstrumentation =
                new DataLoaderDispatcherInstrumentation(dataLoaderRegistry, newOptions().includeStatistics(true));

        Instrumentation instrumentation = new ChainedInstrumentation(
                asList(new TracingInstrumentation(), dlInstrumentation)
        );


        this.graphQL = GraphQL.newGraphQL(graphQLSchema).instrumentation(instrumentation).build();
    }

    //加载解析schema文件
    private GraphQLSchema buildSchema(String sdl) {
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
        RuntimeWiring runtimeWiring = buildWiring();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
    }

    //动态映射类型或方法
    private RuntimeWiring buildWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type(newTypeWiring("Query")
                        .dataFetcher("hero", starWarsWiring.heroDataFetcher)
                        .dataFetcher("human", starWarsWiring.humanDataFetcher)
                        .dataFetcher("droid", starWarsWiring.droidDataFetcher)
                )
                .type(newTypeWiring("Human")
                        .dataFetcher("friends", starWarsWiring.friendsDataFetcher)
                )
                .type(newTypeWiring("Droid")
                        .dataFetcher("friends", starWarsWiring.friendsDataFetcher)
                )

                .type(newTypeWiring("Character")
                        .typeResolver(starWarsWiring.characterTypeResolver)
                )
                .type(newTypeWiring("Episode")
                        .enumValues(starWarsWiring.episodeResolver)
                )
                .build();
    }

    @Bean
    public GraphQL graphQL() {
        return graphQL;
    }

}
