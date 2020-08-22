SpringBoot Webflux 示例
---

Spring WebFlux是Spring Framework 5.0中引入的新的反应式Web框架。
与Spring MVC不同，它不需要Servlet API，完全异步和非阻塞，并通过Reactor项目实现Reactive Streams规范。并且可以在诸如Netty，Undertow和Servlet 3.1+容器的服务器上运行。

## Reactor 中的 Mono 和 Flux

Flux 和 Mono 是 Reactor 中的两个基本概念。Flux 表示的是包含 0 到 N 个元素的异步序列。
在该序列中可以包含三种不同类型的消息通知：正常的包含元素的消息、序列结束的消息和序列出错的消息。
当消息通知产生时，订阅者中对应的方法 onNext(), onComplete()和 onError()会被调用。Mono 表示的是包含 0 或者 1 个元素的异步序列。
该序列中同样可以包含与 Flux 相同的三种类型的消息通知。Flux 和 Mono 之间可以进行转换。
对一个 Flux 序列进行计数操作，得到的结果是一个 Mono<Long>对象。把两个 Mono 序列合并在一起，得到的是一个 Flux 对象。 [了解更多](https://www.ibm.com/developerworks/cn/java/j-cn-with-reactor-response-encode/index.html)

## 示例

1. webflow 
    - 基于SpringMvc注解@Controller
2. webflow-functional 
    - 基于Java8 lambda样式路由和处理