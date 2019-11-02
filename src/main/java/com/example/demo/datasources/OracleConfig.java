package com.example.demo.datasources;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @see Multiple DataSource Configuration SpringBoot
 * @author Praveen Kumar
 * @version %I%, %G%
 * @since 31 October 2019
 * @literal persistence Unit : odba (Oracle)
 */

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
	basePackages = "com.preplaced.multidbconnectionconfig.repository.odba",
	entityManagerFactoryRef = "odbaEntityManagerFactory", 
	transactionManagerRef = "db2PlatformTransactionManager"
)

public class OracleConfig {
	
	@Bean
	@ConfigurationProperties("spring.oracle.datasource")
	public DataSourceProperties odbaDataSourceProperties() {    
			return new DataSourceProperties();
	}
	
	@Bean
	public DataSource odbaDataSource(@Qualifier("odbaDataSourceProperties") DataSourceProperties odbaDataSourceProperties) {    
			return  odbaDataSourceProperties.initializeDataSourceBuilder().build();
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean odbaEntityManagerFactory(
			@Qualifier("odbaDataSource") DataSource odbaDataSource, EntityManagerFactoryBuilder builder) {    
		
		return builder
		.dataSource(odbaDataSource)                      
		.packages("com.example.demo.Model.Employee")
		.persistenceUnit("odba")
		.build();
	}
	
	@Bean
	public PlatformTransactionManager db2PlatformTransactionManager(EntityManagerFactory odbaEntityManagerFactory) {    
		return new JpaTransactionManager(odbaEntityManagerFactory);
	}
}