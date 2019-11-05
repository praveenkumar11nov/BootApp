package com.example.demo.datasources;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

public class TransactionManagerConfig {

	@Bean(name = "chainedTransactionManager")
	public ChainedTransactionManager transactionManager (
			@Qualifier("pgsqlPlatformTransactionManager")PlatformTransactionManager pgsqlTransactionManager,
			@Qualifier("mysqlPlatformTransactionManager")PlatformTransactionManager mysqlTransactionManager,
			@Qualifier("odbaPlatformTransactionManager")PlatformTransactionManager odbaTransactionManager) {

		return new ChainedTransactionManager(pgsqlTransactionManager,mysqlTransactionManager,odbaTransactionManager);
	}
	
}