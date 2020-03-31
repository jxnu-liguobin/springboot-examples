package com.github.dreamylost;

import com.github.dreamylost.Instrumentation.CustomInstrumentation;
import com.github.dreamylost.Instrumentation.FieldValidationBuilder;
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
        //初始化读取gql schema文件
        URL url = Resources.getResource("starWarsSchemaAnnotated.graphqls");
        String sdl = Resources.toString(url, Charsets.UTF_8);
        GraphQLSchema graphQLSchema = buildSchema(sdl);
        //本示例使用DataLoader技术来确保最有效地加载数据（在本例中为StarWars字符），通过graphql上下文对象将其传递给数据获取程序
        //DataLoaderDispatcherInstrumentation也是一种Instrumentation，用来分发dataloader
        DataLoaderDispatcherInstrumentation dlInstrumentation = new DataLoaderDispatcherInstrumentation(dataLoaderRegistry, newOptions().includeStatistics(true));
        //ChainedInstrumentation可以组合多个仪器，TracingInstrumentation是追踪，FieldValidationBuilder是字段验证
        //CustomInstrumentation是自定义的仪器
        Instrumentation instrumentation = new ChainedInstrumentation(asList(FieldValidationBuilder.builder(), new TracingInstrumentation(), new CustomInstrumentation(), dlInstrumentation));
        this.graphQL = GraphQL.newGraphQL(graphQLSchema).instrumentation(instrumentation).build();
    }


    //加载解析schema文件
    private GraphQLSchema buildSchema(String sdl) {
        //读取从resources下加载解析的gql schema文件的字符串
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
        //创建运行时类型织入
        RuntimeWiring runtimeWiring = buildWiring();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        //创建gql schema对象，构造可执行请求的gql
        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
    }

    //动态映射
    private RuntimeWiring buildWiring() {
        return RuntimeWiring.newRuntimeWiring()
                //查询方法
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

                //数据类型
                .type(newTypeWiring("Character")
                        .typeResolver(starWarsWiring.characterTypeResolver)
                )
                .type(newTypeWiring("Episode")
                        .enumValues(starWarsWiring.episodeResolver)
                )
                .build();
    }

    //作为bean，使用该bean执行gql请求
    @Bean
    public GraphQL graphQL() {
        return graphQL;
    }

}
