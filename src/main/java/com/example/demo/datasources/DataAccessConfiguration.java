package com.example.demo.datasources;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
 * @literal persistence Unit : odba (Oracle) , pgsql (PostgresSql) , mysql (MySql)
 */

@Configuration
public class DataAccessConfiguration {

	private static final String MODEL_PATH = "com.example.demo.Model";
	
	/* ==================================================================================================
	   ================================== MySql DataBase Configuration ==================================
	   ================================================================================================== */
	
	@Configuration
	@EnableTransactionManagement
	@EnableJpaRepositories(
		basePackages = "com.preplaced.multidbconnectionconfig.repository.mysql",
		entityManagerFactoryRef = "mysqlEntityManagerFactory", 
		transactionManagerRef   = "mysqlPlatformTransactionManager"
	)

	public class MysqlConfig {
		
		@Bean
		@ConfigurationProperties("spring.mysql.datasource")
		public DataSourceProperties mysqlDataSourceProperties() {    
				return new DataSourceProperties();
		}
		
		@Bean
		public DataSource mysqlDataSource(@Qualifier("mysqlDataSourceProperties") DataSourceProperties mysqlDataSourceProperties) {    
				return mysqlDataSourceProperties.initializeDataSourceBuilder().build();
		}
		
		@Bean
		public LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactory(
				@Qualifier("mysqlDataSource") DataSource mysqlDataSource, EntityManagerFactoryBuilder builder) {    
			
			return builder
			.dataSource(mysqlDataSource)                      
			.packages(MODEL_PATH)
			.persistenceUnit("mysql")
			.build();
		}
		
		@Bean
		public PlatformTransactionManager mysqlPlatformTransactionManager(EntityManagerFactory mysqlEntityManagerFactory) {    
			return new JpaTransactionManager(mysqlEntityManagerFactory);
		}
	}
	
	/* ==================================================================================================
	   ================================== PostgresSql DataBase Configuration ============================
	   ================================================================================================== */
	
	@Configuration
	@EnableTransactionManagement
	@EnableJpaRepositories(
		basePackages = "com.preplaced.multidbconnectionconfig.repository.pgsql",
		entityManagerFactoryRef = "pgsqlEntityManagerFactory",
		transactionManagerRef   = "pgsqlPlatformTransactionManager"
	)

	public class PgSqlConfig  {

		@Primary
		@Bean
		@ConfigurationProperties("spring.datasource")
		public DataSourceProperties pgsqlDataSourceProperties() {
			return new DataSourceProperties();
		}

		@Primary
		@Bean
		public DataSource pgsqlDataSource(@Qualifier("pgsqlDataSourceProperties")DataSourceProperties pgsqlDataSourceProperties) {    
			return pgsqlDataSourceProperties.initializeDataSourceBuilder().build();
		}
		
		@Primary
		@Bean
		public LocalContainerEntityManagerFactoryBean pgsqlEntityManagerFactory(
				@Qualifier("pgsqlDataSource") DataSource pgsqlDataSource,EntityManagerFactoryBuilder builder) {
			
			return builder
			.dataSource(pgsqlDataSource)
			.packages(MODEL_PATH)
			.persistenceUnit("pgsql")
			.build();
		}
		
		@Primary
		@Bean
		public PlatformTransactionManager pgsqlPlatformTransactionManager(
				@Qualifier("pgsqlEntityManagerFactory")EntityManagerFactory pgsqlEntityManagerFactory) {    
			return new JpaTransactionManager(pgsqlEntityManagerFactory);
		}
	}
	
	/* ==================================================================================================
	   ================================== Oracle DataBase Configuration =================================
	   ================================================================================================== */
	
	@Configuration
	@EnableTransactionManagement
	@EnableJpaRepositories(
		basePackages = "com.preplaced.multidbconnectionconfig.repository.odba",
		entityManagerFactoryRef = "odbaEntityManagerFactory", 
		transactionManagerRef   = "odbaPlatformTransactionManager"
	)

	public class OracleConfig {
		
		@Bean
		@ConfigurationProperties("spring.oracle.datasource")
		public DataSourceProperties odbaDataSourceProperties() {    
				return new DataSourceProperties();
		}
		
		@Bean
		public DataSource odbaDataSource(@Qualifier("odbaDataSourceProperties") DataSourceProperties odbaDataSourceProperties) {    
				return odbaDataSourceProperties.initializeDataSourceBuilder().build();
		}
		
		@Bean
		public LocalContainerEntityManagerFactoryBean odbaEntityManagerFactory(
				@Qualifier("odbaDataSource") DataSource odbaDataSource, EntityManagerFactoryBuilder builder) {    
			
			return builder
			.dataSource(odbaDataSource)                      
			.packages(MODEL_PATH)
			.persistenceUnit("odba")
			.build();
		}
		
		@Bean
		public PlatformTransactionManager odbaPlatformTransactionManager(EntityManagerFactory odbaEntityManagerFactory) {    
			return new JpaTransactionManager(odbaEntityManagerFactory);
		}
	}
}
