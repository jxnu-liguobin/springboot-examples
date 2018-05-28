package cn.edu.jxnu.swaggerTest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger配置
 * 
 * @author 梦境迷离.
 * @time 2018年5月28日
 * @version v1.0
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	/**
	 * swagger2的配置文件，这里可以配置swagger2的一些基本的内容，比如扫描的包等等
	 * 
	 * @author 梦境迷离.
	 * @time 2018年5月28日
	 * @version v1.0
	 * @return
	 */
	@Bean
	public Docket createRestApi() {
		ParameterBuilder tokenPar = new ParameterBuilder();
		List<Parameter> pars = new ArrayList<>();
		tokenPar.name("token").description("令牌").modelRef(new ModelRef("string")).parameterType("query").required(false)
				.build();
		pars.add(tokenPar.build());
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("cn.edu.jxnu.swaggerTest")).paths(PathSelectors.any()).build()
				.globalOperationParameters(pars);
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				// 页面标题
				.title("个人测试")
				// 创建人
				.description("个人测试用API").termsOfServiceUrl("https://blog.csdn.net/qq_34446485")
				// 创建人
				.contact("测试")
				// 版本号
				.version("1.0").build();
	}
}
