package com.github.dreamylost.booking.config;

import com.github.dreamylost.booking.util.BookingFlowHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.webflow.mvc.servlet.FlowHandlerAdapter;
import org.springframework.webflow.mvc.servlet.FlowHandlerMapping;
import org.springframework.webflow.mvc.view.AjaxUrlBasedViewResolver;
import org.springframework.webflow.mvc.view.FlowAjaxTiles3View;


/**
 * web mnv配置
 */
@EnableWebMvc
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private WebFlowConfig webFlowConfig;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/", "classpath:/META-INF/web-resources/");
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("intro");
        registry.addViewController("/login");
        registry.addViewController("/logoutSuccess");
    }

    //3.流程处理
    @Bean
    public FlowHandlerMapping flowHandlerMapping() {
        FlowHandlerMapping handlerMapping = new FlowHandlerMapping();
        handlerMapping.setOrder(-1);
        handlerMapping.setFlowRegistry(this.webFlowConfig.flowRegistry());
        return handlerMapping;
    }

    //4.流程处理适配器  是DispatcherServlet和Spring Web Flow之间的桥梁
    @Bean
    public FlowHandlerAdapter flowHandlerAdapter() {
        FlowHandlerAdapter handlerAdapter = new FlowHandlerAdapter();
        handlerAdapter.setFlowExecutor(this.webFlowConfig.flowExecutor());
        handlerAdapter.setSaveOutputToFlashScopeOnRedirect(true);
        return handlerAdapter;
    }

    //5.流程处理器的实现
    @Bean(name = "hotels/booking")
    public BookingFlowHandler BookingFlowHandler() {
        return new BookingFlowHandler();
    }

    //tiles
    @Bean
    public AjaxUrlBasedViewResolver viewResolver() {
        AjaxUrlBasedViewResolver resolver = new AjaxUrlBasedViewResolver();
        resolver.setViewClass(FlowAjaxTiles3View.class);
        return resolver;
    }

    @Bean
    public TilesConfigurer tilesConfigurer() {
        TilesConfigurer configurer = new TilesConfigurer();
        configurer.setDefinitions("/WEB-INF/**/views.xml");
        return configurer;
    }

}
