package com.example.demo.ServiceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Model.PaymentGroups;
import com.example.demo.Model.Payments;
import com.example.demo.Service.PaymentService;

@Service
public class PaymentServiceImpl extends GenericServiceImpl<Payments> implements PaymentService{

	@Override
	public Payments findById(int id) {
		try {
			return (Payments) getCustomEntityManager("pgsql").createNativeQuery("SELECT * FROM payments WHERE id=" + id).getSingleResult();
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<?> getPayDetails(String grpname) {
		try {
			return getCustomEntityManager("pgsql").createNativeQuery("SELECT * FROM bootapp.getpaymentbygroup('"+grpname+"')").getResultList();
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<Payments> getPayments(){
		try {
			System.out.println("inside payment service impl...............");
			List<Payments> payments=new ArrayList<>();
			Payments pay;
			String query="SELECT * FROM bootapp.payments WHERE remarks NOT LIKE 'DummyEntry' ORDER BY id DESC";
			List<?> list=getCustomEntityManager("pgsql").createNativeQuery(query).getResultList();		
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Object[] object = (Object[]) iterator.next();
				pay=new Payments();
				pay.setId(Integer.parseInt(object[0].toString()));
				pay.setPersonname(object[1].toString());
				pay.setGroupname(object[2].toString());
				pay.setPaydate(new SimpleDateFormat("yyyy-MM-dd").parse(object[3].toString()));
				pay.setRemarks(object[4].toString());
				pay.setAmount(object[5].toString());
				
				payments.add(pay);
			}
			return payments;			
		}
		catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<Payments> getAllPayments(String grpname){
		try {
			System.out.println("inside payment service impl...............");
			List<Payments> payments=new ArrayList<>();
			Payments pay;
			String query="SELECT * FROM bootapp.payments WHERE groupname LIKE '"+grpname+"' ORDER BY id DESC";
			List<?> list=getCustomEntityManager("pgsql").createNativeQuery(query).getResultList();		
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Object[] object = (Object[]) iterator.next();
				pay=new Payments();
				pay.setId(Integer.parseInt(object[0].toString()));
				pay.setPersonname(object[1].toString());
				pay.setGroupname(object[2].toString());
				pay.setPaydate(new SimpleDateFormat("yyyy-MM-dd").parse(object[3].toString()));
				pay.setRemarks(object[4].toString());
				pay.setAmount(object[5].toString());
				
				payments.add(pay);
			}
			
			return payments;			
		}
		catch (Exception e) {
			return null;
		}
	}

	@Override
	@Transactional
	public String createGroup(String groupname) {
		try {
			String insert="INSERT INTO bootapp.paygroups (groupname,createdon)VALUES('"+groupname+"',TO_DATE('"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"','yyyy-MM-dd'))";
			getCustomEntityManager("pgsql").createNativeQuery(insert).executeUpdate();
			return "Group created successfully";
		} 
		catch (Exception e) {
			e.printStackTrace();
			return "Exception came while creating group !";
		}
	}

	@Override
	@Transactional
	public String addGroupMembers(String groupname, String personname) {
		try {
			String insert="INSERT INTO bootapp.groupmember (personname,groupname,addedon)VALUES('"+personname+"','"+groupname+"',TO_DATE('"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"','yyyy-MM-dd'))";
			getCustomEntityManager("pgsql").createNativeQuery(insert).executeUpdate();
			return "Group Memeber added successfully";
		} 
		catch (Exception e) {
			e.printStackTrace();
			return "Exception came while adding group member !";
		}
	}

	@Override
	@Transactional
	public String addPaymentInGroup(String groupname, String personname, String remarks, String amount) {
		
		try {
			String dummyentry="SELECT count(*) FROM bootapp.payments WHERE groupname LIKE '"+groupname+"'";
			int count=Integer.parseInt(getCustomEntityManager("pgsql").createNativeQuery(dummyentry).getSingleResult().toString());
			if(count==0) {
				String grpmembers="SELECT * FROM bootapp.groupmember WHERE groupname LIKE '"+groupname+"'";
				List<?> members=getCustomEntityManager("pgsql").createNativeQuery(grpmembers).getResultList();
				for (Iterator iterator = members.iterator(); iterator.hasNext();) {
					Object[] object = (Object[]) iterator.next();
					String dummy="INSERT INTO bootapp.payments(personname,groupname,paydate,remarks,amount)VALUES('"+object[1]+"','"+groupname+"',TO_DATE('"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"','yyyy-MM-dd'),'DummyEntry',0)";
					getCustomEntityManager("pgsql").createNativeQuery(dummy).executeUpdate();
				}
			}
		} 
		catch (Exception e) {
			return "Exception came while adding payment1 !";
		}
		
		try {
			String insert="INSERT INTO bootapp.payments(personname,groupname,paydate,remarks,amount)VALUES('"+personname+"','"+groupname+"',TO_DATE('"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"','yyyy-MM-dd'),'"+remarks+"',"+amount+")";
			getCustomEntityManager("pgsql").createNativeQuery(insert).executeUpdate();
			return "Payment added successfully";
		} 
		catch (Exception e) {
			e.printStackTrace();
			return "Exception came while adding payment2 !";
		}
	}

	@Override
	public List<?> seeGroupWiseExpense(String groupname) {
		try {
			return getCustomEntityManager("pgsql").createNativeQuery("SELECT * FROM bootapp.getpaymentbygroup('"+groupname+"')").getResultList();			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<PaymentGroups> getGroupNamesM() {
		PaymentGroups paygroup;
		List<PaymentGroups> paymentgroups=new ArrayList<>();
		List<?> list=getCustomEntityManager("pgsql").createNativeQuery("SELECT * FROM bootapp.paygroups ORDER BY id DESC").getResultList();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Object[] object = (Object[]) iterator.next();
			paygroup=new PaymentGroups();
			paygroup.setId(Integer.parseInt(object[0].toString()));
			paygroup.setGroupname(object[1].toString());
			paymentgroups.add(paygroup);
		}
		return paymentgroups;
	}

	@Override
	public List<?> getPersonInThisGroup(String groupname) {
		return getCustomEntityManager("pgsql").createNativeQuery("SELECT id,personname FROM bootapp.groupmember WHERE groupname LIKE '"+groupname+"'").getResultList();
	}

	@Override
	@Transactional
	public void addPaymentInGroupAndSharedExpense(String exppersonname, String expdtperson,String groupname,String remarks) {
		try {
			String insert="INSERT INTO bootapp.sharedexpence(name,groupname,amount,dated,remarks)VALUES('"
							+exppersonname+"','"+groupname+"',"+expdtperson+",TO_DATE('"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"','yyyy-MM-dd'),'"+remarks+"')";
			getCustomEntityManager("pgsql").createNativeQuery(insert).executeUpdate();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public List<?> viewSharedExpense(String groupname) {
		try {
			return getCustomEntityManager("pgsql").createNativeQuery("SELECT * FROM bootapp.detailshareddexpenses('"+groupname+"')").getResultList();			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
