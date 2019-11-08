package com.mywallet.commom.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

/**
 * WebConfig
 *
 * @author linapex
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
//        registry.addInterceptor(commonInterceptor).addPathPatterns(INTERCEPTORS_PATTERNS);
    }

    @Bean(name = "simpleMappingExceptionResolver")
    public SimpleMappingExceptionResolver createExceptionResolver() {
        SimpleMappingExceptionResolver extException = new SimpleMappingExceptionResolver();
        extException.setDefaultErrorView("/error");
        extException.setExceptionAttribute("exception");
        extException.setWarnLogCategory("WARN");
        return extException;
    }

}