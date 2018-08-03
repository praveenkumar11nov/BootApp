package com.example.demo.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.Model.Users;

@Repository
public interface UserRepository extends CrudRepository<Users, Integer> {
	
	Users findById(int id);
	
	@Query(value="SELECT * FROM USERS WHERE NAME='praveen' AND PASSWORD='123'", nativeQuery = true)
	Users findByNameAndPassword(String name,String pwd);
	
	/*
	Query q = em.createNativeQuery("SELECT a.firstname, a.lastname FROM Author a WHERE a.id = ?");
	q.setParameter(1, 1);
	
	*/
	
}
