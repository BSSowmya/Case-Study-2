package com.javamaster.user.authentication;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {
    @Bean
    public FilterRegistrationBean<AuthTokenFilter> jwtFilter() {
        FilterRegistrationBean<AuthTokenFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new AuthTokenFilter());
        filterRegistrationBean.addUrlPatterns("/auth/**"); // Specify the URL patterns where you want the filter to be applied
        return filterRegistrationBean;
    }
}
