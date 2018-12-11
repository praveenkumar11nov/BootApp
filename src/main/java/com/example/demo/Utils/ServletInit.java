package com.example.demo.Utils;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServletInit extends SpringBootServletInitializer {
   @Bean
   public FilterRegistrationBean siteMeshFilter() {
       FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
       filterRegistrationBean.setFilter(new MySiteMeshFilter());
       filterRegistrationBean.addUrlPatterns("/*");
       return filterRegistrationBean;
   }
}