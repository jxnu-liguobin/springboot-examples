springboot-exmaples
--

子模块之间相互无关联

> cxf

```
整合SpringBoot MySQL Mybatis CXF/JSR-RS 
实现WebServices
实现Restful
```
     
* 使用 
```
1）mysql中新建demo库运行resources下sql文件
2）修改application.properties修改数据源
3）启动主类
```

> mongodb

* 使用
```
启动主类
新增 127.0.0.1:9000/save
查询单个  127.0.0.1:9000/findByName
查询List  127.0.0.1:9000/find
```
    
> swagger

* 使用
```
启动主类
访问localhost:8080/swagger-ui.html
```

> graphql

* 使用
```
1.启动 Application.java
2.访问http://localhost:8080/
3.查询
调用方法1：无参方法
{
  hello
}
调用方法2：有参方法
{
  echo(toEcho:"charming")
}
```

> graphql-complete

```
1.启动 Application.java
2.访问http://localhost:8080/
3.查询
调用droid方法：
{
  droid(id: "1001") {
    id
  }
}
调用hero方法：
{
  hero(episode: NEWHOPE) {
    id
  }
}
其他方法参考 starWarsSchemaAnnotated.graphqls
```