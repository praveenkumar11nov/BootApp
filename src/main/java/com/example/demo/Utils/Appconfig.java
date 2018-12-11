package com.example.demo.Utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.example.demo.security.SecurityConfig;
/*
@EnableWebMvc
@Configuration
@ComponentScan({"com.example.demo.*"})
@Import({ SecurityConfig.class })*/
public class Appconfig {
	
	//@Bean(name="dataSource")
	public DriverManagerDataSource dataSource() {
	    DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
	    driverManagerDataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
	    driverManagerDataSource.setUrl("jdbc:oracle:thin:@192.168.2.96:1521:ORCL");
	    driverManagerDataSource.setUsername("SPG");
	    driverManagerDataSource.setPassword("bcits");
	    return driverManagerDataSource;
	}
	
	//@Bean
	public InternalResourceViewResolver viewResolver() {
	    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
	    viewResolver.setViewClass(JstlView.class);
	    viewResolver.setPrefix("/WEB-INF/pages/");
	    viewResolver.setSuffix(".jsp");
	    return viewResolver;
	}
}
