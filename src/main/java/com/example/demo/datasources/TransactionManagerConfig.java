package com.example.demo.datasources;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

public class TransactionManagerConfig {

	@Bean(name = "chainedTransactionManager")
	public ChainedTransactionManager transactionManager (
			@Qualifier("pgsqlPlatformTransactionManager")PlatformTransactionManager pgsqlTransactionManager,
			@Qualifier("db2PlatformTransactionManager")PlatformTransactionManager db2TransactionManager)
	{
	
		return new ChainedTransactionManager(db2TransactionManager,pgsqlTransactionManager);
	}
	
}