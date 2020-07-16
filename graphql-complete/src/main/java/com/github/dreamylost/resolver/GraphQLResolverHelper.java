package com.github.dreamylost.resolver;

import com.github.dreamylost.domain.Episode;
import com.github.dreamylost.domain.FilmCharacter;
import com.github.dreamylost.domain.Human;
import com.github.dreamylost.scalar.EmailScalar;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLScalarType;
import graphql.schema.TypeResolver;
import graphql.schema.idl.EnumValuesProvider;

import java.util.List;

/**
 * @author liguobin@growingio.com
 * @version 1.0, 2020/3/31
 */
public class GraphQLResolverHelper {

    //TypeResolver帮助graphql-java决定具体值属于哪种类型。接口和联合需要此功能。
    /**
     * graphql类型系统中的角色是一个接口，需要确定要返回的具体graphql对象类型
     */
    public static TypeResolver characterTypeResolver = environment -> {
        //类型解析
        FilmCharacter character = environment.getObject();
        if (character instanceof Human) {
            //人类
            return (GraphQLObjectType) environment.getSchema().getType("Human");
        } else {
            //机器人
            return (GraphQLObjectType) environment.getSchema().getType("Droid");
        }
    };

    //枚举类型解析
    public static EnumValuesProvider episodeResolver = Episode::valueOf;

    public static GraphQLScalarType Email = EmailScalar.Email;
}
