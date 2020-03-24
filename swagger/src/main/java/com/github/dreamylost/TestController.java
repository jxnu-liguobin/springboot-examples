package com.github.dreamylost;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 浏览器访问 http://localhost:8080/swagger-ui.html
 */
@RestController
@RequestMapping("test")
// 这个注解用于整个类上，对整个类中的接口列表进行简单说明
@Api(value = "测试接口Controller")
public class TestController {

    // value 和notes都是对接口的说明，是显示的位置不一样
    // httpMethod 是接口的提交方法，如果不写的话swagger会把所有的提交方式都展示一遍就像这样
    @ApiOperation(value = "测试用接口", notes = "测试用接口", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "用户姓名", dataType = "String", required = true, paramType = "form"),
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", required = false, paramType = "form")})
    @RequestMapping("word")
    public String HelloWord(String name, Integer id) {
        return "Hello Word";
    }

}