package cn.edu.jxnu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Controller;

/**
 * 启动类
 * 
 * @author： liguobin
 * @Description：
 * @时间： 2018-3-7 下午3:41:24
 * @version： V1.0
 * 
 */
@Controller
@MapperScan("cn.edu.jxnu.dao")
@SpringBootApplication
// / 没有这个rest失效 只存在soap
@ImportResource(locations = { "classpath:cxf-config.xml" })
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
