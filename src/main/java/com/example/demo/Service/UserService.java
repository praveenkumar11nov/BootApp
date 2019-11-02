package com.example.demo.Service;

import java.util.List;

import com.example.demo.Model.Users;

public interface UserService extends GenericService<Users>{

	Users findByNameAndPassword(String user,String pwd);
	List<?> getUserFromDB(String user,String pwd);
	
	void findByIdTesingPostgresSqlDataSource(int id);
	void findByIdTesingMsqlDataSource(int id);
	void findByIdTesingOracleDataSource(int id);
}
