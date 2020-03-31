package com.github.dreamylost;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 处理/graphql请求
 *
 * @author 梦境迷离
 * @time 2020年03月24日17:57:08
 */
@RestController
public class GraphQLController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private GraphQLProvider graphQLProvider;

    //前端js中写死该URI
    @RequestMapping(value = "/graphql", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public void graphqlGET(@RequestParam("query") String query,
                           @RequestParam(value = "operationName", required = false) String operationName,
                           @RequestParam("variables") String variablesJson,
                           HttpServletResponse httpServletResponse) throws IOException {
        if (query == null) {
            query = "";
        }
        Map<String, Object> variables = new LinkedHashMap<>();
        ;
        if (variablesJson != null) {
            variables = objectMapper.readValue(variablesJson, new TypeReference<Map<String, Object>>() {
            });
        }
        graphQLProvider.executeGraphqlQuery(httpServletResponse, operationName, query, variables);
    }


    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/graphql", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public void graphql(@RequestBody Map<String, Object> body, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        String query = (String) body.get("query");
        if (query == null) {
            query = "";
        }
        String operationName = (String) body.get("operationName");
        Map<String, Object> variables = (Map<String, Object>) body.get("variables");
        if (variables == null) {
            variables = new LinkedHashMap<>();
        }
        //执行请求， 操作 查询 变量参数
        graphQLProvider.executeGraphqlQuery(httpServletResponse, operationName, query, variables);
    }
}
