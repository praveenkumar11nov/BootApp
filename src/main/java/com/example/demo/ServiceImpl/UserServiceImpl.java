package com.example.demo.ServiceImpl;

import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;
import com.example.demo.Model.Users;
import com.example.demo.Service.UserService;

@Service
public class UserServiceImpl extends GenericServiceImpl<Users> implements UserService{

	
	public Users findByNameAndPassword(String user,String pwd) {
		try {
			return (Users) pgsql.createNativeQuery("SELECT * FROM bootapp.USERS WHERE username='praveen' AND password='123'").getSingleResult();			
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<?> getUserFromDB(String user, String pwd) {
		try {
			List<?> list= pgsql.createNativeQuery("SELECT * FROM praveen.USERS WHERE NAME='"+user+"' AND PASSWORD='"+pwd+"'").getResultList();
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Object[] object = (Object[]) iterator.next();
				
				System.out.println("User = " + object[1] + " Password = " + object[2]);
			}
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void findByIdTesingPostgresSqlDataSource(int id) {
		try {
			Object[] user1 = (Object[]) getCustomEntityManager("pgsql").createNativeQuery("SELECT * FROM bootapp.USERS WHERE id=" + id).getSingleResult();
			System.out.println("PostgresSql DBname : mydatabase ====" + user1[0] + "====" + user1[1] + "====" + user1[2] 
					+ "====" + user1[3] + "====" + user1[4] + "====" + user1[5] + "====" + user1[6]);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void findByIdTesingMsqlDataSource(int id) {
		try {
			Object[] user1 = (Object[]) getCustomEntityManager("db2").createNativeQuery("SELECT * FROM aws.users where id=" + id).getSingleResult();
			System.out.println("MySql DBname : aws ====" + user1[0] + "====" + user1[1] + "====" + user1[2] 
					+ "====" + user1[3] + "====" + user1[4] + "====" + user1[5]);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void findByIdTesingOracleDataSource(int id) {
		try {
			//Object[] user1 = (Object[]) getCustomEntityManager("odba").createNativeQuery("SELECT * FROM USERS WHERE ID=" + id).getSingleResult();
			//System.out.println("ORACLE DB : BSMARTJVVNL ====" + user1[0] + "====" + user1[1] + "====" + user1[2] 
			//		+ "====" + user1[3] + "====" + user1[4] + "====" + user1[5] + "====" + user1[6]);
			
			Object[] user1 = (Object[]) getCustomEntityManager("odba").createNativeQuery(
	"SELECT ID,RRNUMBER,SITECODE,ACCOUNTID,RECEIPTNO,AMOUNT,CREATEDDATE FROM BSMARTJVVNL.ONLINE_PAYMENT WHERE RECEIPTNO='PCPI6832242118'"
			).getSingleResult();
			
			System.out.println("ORACLE DB : BSMARTJVVNL ====" + user1[0] + "====" + user1[1] + "====" + user1[2] 
						+ "====" + user1[3] + "====" + user1[4] + "====" + user1[5] + "====" + user1[6]);

			
			Object[] user2 = (Object[]) getCustomEntityManager("odba").createNativeQuery(
					"SELECT ID,SDOCODE,ACCNO,KNO,RECNO,AMOUNT,RDATE FROM DH2_2109120.PAYMENTS WHERE RECNO='PCPI6832242118'"
					).getSingleResult();
			
			System.out.println("ORACLE DB : DH2_2109120 ====" + user2[0] + "====" + user2[1] + "====" + user2[2] 
					+ "====" + user2[3] + "====" + user2[4] + "====" + user2[5] + "====" + user2[6]);
			
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
