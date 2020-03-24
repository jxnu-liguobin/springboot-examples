springboot-exmaples
--

$ 表示子模块是依赖父级项目的（子模块本身就是多个模块组成的，如dubbo）

> cxf  

```
整合SpringBoot MySQL Mybatis CXF/JSR-RS 
实现WebServices
实现Restful
```
     
* 使用 
```
1.mysql中新建demo库运行resources下sql文件
2.修改application.properties修改数据源
3.启动Application.java类
```

> mongodb 

* 使用
```
1.启动Application.java类
1.新增 127.0.0.1:9000/save
2.查询单个  127.0.0.1:9000/findByName
3.查询List  127.0.0.1:9000/find
```
    
> swagger 

* 使用
```
1.启动Application.java类
2.访问localhost:8080/swagger-ui.html
```

> graphql 

* 使用
```
1.启动Application.java类
2.访问http://localhost:8080/
3.查询
调用方法hello：无参方法
{
  hello
}
调用方法echo：有参方法
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
调用hero方法：枚举参数
{
  hero(episode: NEWHOPE) {
    id
  }
}
其他方法参考 starWarsSchemaAnnotated.graphqls
```

> dubbo $
```
1.启动zookeeper
2.启动dubbo-admin客户端(需自己将官方的war放进tomcat的webapps/ROOT下) tomcat端口
3.启动生产者(dubbo-provider DubboProviderLauncher.java类) 端口8081
4.启动消费者(duboo-consumer DubboConsumerLauncher.java类) 端口8082
5.查询
    1）浏览器输入: http://127.0.0.1:8082/sayHello/dubbo
    2）输出String: Hello dubbo
    3）浏览器输入:http://localhost:8082/getCity
    4）输出JSON/对象:
        {
            "id": 1,
            "provinceId": 2,
            "cityName": "南昌",
            "description": "是我的故乡"
        }
使用dubbo的管理平台(dubbo-admin应用放在ROOT下则路径:http://localhost:8088) 查看结果,初始账户为:root/root
```
> spring cloud

SpringBoot 1.5.13
SpringCloud Dalston.SR5

包含组件说明

* Eureka  
* Ribbon  
* Config  
* Hystrix 

模块说明

* 服务注册中心（cloud-central） 
* hello服务提供者（cloud-hello-provider） 
* user服务提供者（cloud-user-provider）
* 负载均衡/断路器（cloud-ribbon-consumer）                
* 配置中心 服务端（cloud-config-server）          
* 配置调用 客户端（cloud-config-client）  

```
1.启动cloud-central，修改 spring.profiles.active=peer2，再运行一个实例
2.启动cloud-hello-provider 支持启动多实例（随机端口）
3.启动cloud-user-provider 支持启动多实例（随机端口）
4.启动cloud-ribbon-consumer
5.启动cloud-config-server
6.启动cloud-config-client

```         