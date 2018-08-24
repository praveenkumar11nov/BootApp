package com.example.demo.ServiceImpl;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.Model.Users;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private EntityManagerFactory entityManagerFactory;
	
	

	@Override
	public Users findById(int id) {
		try {
			return userRepo.findById(id);
		} catch (Exception e) {
			return null;
		}
	}
	
	public Users findByNameAndPassword(String user,String pwd) {
		System.err.println("..............findByNameAndPassword..............\n" + userRepo.findByNameAndPassword(user,pwd));
		try {
			return userRepo.findByNameAndPassword(user,pwd);			
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<?> getUserFromDB(String user, String pwd) {
		
		
		try {
			EntityManager entityManager = entityManagerFactory.createEntityManager();
			List<?> list= entityManager.createNativeQuery("SELECT * FROM praveen.USERS WHERE NAME='"+user+"' AND PASSWORD='"+pwd+"'").getResultList();
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Object[] object = (Object[]) iterator.next();
				
				System.out.println("User = " + object[1] + " Password = " + object[2]);
			}
			return list;
		} catch (Exception e) {
			return null;
		}
	}
	

}
