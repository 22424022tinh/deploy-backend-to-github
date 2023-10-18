package com.vn.hcmus.qlqtpm.backendvnexpress.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<HttpLoggingConfig> loggingFilter() {
        FilterRegistrationBean<HttpLoggingConfig> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new HttpLoggingConfig());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}
