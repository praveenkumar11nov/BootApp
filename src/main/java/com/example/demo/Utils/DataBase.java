package com.example.demo.Utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;

public class DataBase {

	@Autowired
	private static EntityManagerFactory entityManagerFactory;
	
	public static EntityManager entityManager = entityManagerFactory.createEntityManager();
	
}
