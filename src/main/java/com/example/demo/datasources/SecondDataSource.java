package com.example.demo.datasources;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import static java.util.Collections.singletonMap;

//@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = "secondEntityManagerFactory",
        transactionManagerRef = "secondTransactionManager",
        basePackages = "com.example.demo.Repository.SecondRepository"
)

@EnableTransactionManagement
public class SecondDataSource {

    @Bean(name = "secondEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean secondEntityManagerFactory(
    		final EntityManagerFactoryBuilder builder,final @Qualifier("spring.mysql.datasource") DataSource dataSource){

        return builder
                .dataSource(dataSource)
                .packages("com.example.demo.Model")
                .persistenceUnit("secondDb")
                .properties(singletonMap("hibernate.hbm2ddl.auto", "create-drop"))
                .build();
    }

    @Bean(name = "secondTransactionManager")
    public PlatformTransactionManager secondTransactionManager(
    		@Qualifier("secondEntityManagerFactory")EntityManagerFactory secondEntityManagerFactory) {

        return new JpaTransactionManager(secondEntityManagerFactory);
    }

}