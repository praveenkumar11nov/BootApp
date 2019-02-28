package com.example.demo.datasources;

import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import javax.sql.DataSource;

//@Configuration
public class CloudConfig extends AbstractCloudConfig {

    @Primary
    @Bean(name = "spring.datasource")
    public DataSource firstDataSource() {
        return connectionFactory().dataSource("spring.datasource");
    }

    @Bean(name = "spring.mysql")
    public DataSource secondDataSource() {
        return connectionFactory().dataSource("spring.mysql");
    }

}