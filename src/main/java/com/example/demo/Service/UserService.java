package com.example.demo.Service;

import java.util.List;

import com.example.demo.Model.Users;

public interface UserService {

	Users findById(int id);
	Users findByNameAndPassword(String user,String pwd);
	List<?> getUserFromDB(String user,String pwd);
	
	void save(Users users);
}
