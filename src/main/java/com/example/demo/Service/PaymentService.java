package com.example.demo.Service;

import java.util.List;

import com.example.demo.Model.PaymentGroups;
import com.example.demo.Model.Payments;

public interface PaymentService extends GenericService<Payments>{
    Payments findById(int id);
	List<?> getPayDetails(String grpname);
	List<Payments> getPayments();
	List<Payments> getAllPayments(String grpname);
	String createGroup(String groupname);
	String addGroupMembers(String groupname, String personname);
	String addPaymentInGroup(String groupname, String personname, String remarks, String amount);
	List<?> seeGroupWiseExpense(String groupname);
	List<PaymentGroups> getGroupNamesM();
	List<?> getPersonInThisGroup(String groupname);
	void addPaymentInGroupAndSharedExpense(String exppersonname, String expdtperson,String groupname,String remarks);
	List<?> viewSharedExpense(String groupname);
	
	
}
