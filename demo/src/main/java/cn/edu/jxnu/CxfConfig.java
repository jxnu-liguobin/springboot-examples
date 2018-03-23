package cn.edu.jxnu;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import cn.edu.jxnu.serviceImpl.StudentServiceImpl;

/**
 * 
 * @author： liguobin @Description： @时间： 2018-3-7 下午4:11:57 @version： V1.0
 * 
 */
@Configuration
public class CxfConfig {

	@Bean
	public ServletRegistrationBean newServlet() {
		return new ServletRegistrationBean(new CXFServlet(), "/cxf/*");
	}

	@Bean(name = Bus.DEFAULT_BUS_ID)
	public SpringBus springBus() {
		return new SpringBus();
	}

	/**
	 * @return
	 */
	@Bean
	@Qualifier("studentServiceImpl") // 注入webService
	public Endpoint endpoint(StudentServiceImpl studentServiceImpl) {
		EndpointImpl endpoint = new EndpointImpl(springBus(), studentServiceImpl);
		endpoint.publish("/webService");// 暴露webService api
		return endpoint;
	}

	@Bean("jsonProvider") // 构造一个json转化bean，用于将student转化为json
	public JacksonJsonProvider getJacksonJsonProvider() {
		return new JacksonJsonProvider();

	}
}