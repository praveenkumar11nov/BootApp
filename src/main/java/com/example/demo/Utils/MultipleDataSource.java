package com.example.demo.Utils;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

public class MultipleDataSource{
/*
	@Bean
    @Primary
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().build();
    }
 
    @Bean
    @ConfigurationProperties(prefix="spring.datasource2")
    public DataSource secondaryDataSource() {
        return DataSourceBuilder.create().build();
    }
*/
}
