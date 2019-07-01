package com.example.demo.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.Model.PaymentGroups;
import com.example.demo.Model.Payments;
import com.example.demo.Service.PaymentService;
import com.example.demo.Service.UserService;

@Controller
@RequestMapping(value="/app")
public class PaymentController {

	@Autowired
	private PaymentService payservice;

	@RequestMapping(value="/paygroup")
	public String goToPay(ModelMap model) {
		System.out.println("Inside goToManualDecor1");
		
		List<Payments> payments = payservice.getPayments();
		model.addAttribute("payments",payments);
		
		List<PaymentGroups> paymentgroups=payservice.getGroupNamesM();
		model.addAttribute("paymentgroups",paymentgroups);
		
		return "ThemePages/paygroup";
	}
	
	@RequestMapping(value="/paygrouppage")
	public String goToPaymentPageWithoutLogin(ModelMap model) {
		System.out.println("Inside goToManualDecor1");
		
		List<Payments> payments = payservice.getPayments();
		model.addAttribute("payments",payments);
		
		System.out.println("payments ======= " + payments);
		
		List<PaymentGroups> paymentgroups=payservice.getGroupNamesM();
		model.addAttribute("paymentgroups",paymentgroups);
		
		System.out.println("paymentgroups ======= " + paymentgroups);
		
		return "ThemePages/paygroup";
	}

	@RequestMapping(value="/getAllPayments") 
	public @ResponseBody List<Payments> getAllPayments() {
		System.out.println("Inside getAllPayments");
		List<Payments> payments = payservice.getPayments();
		return payments;
	}
	
	@RequestMapping(value="/createGroup") 
	public @ResponseBody List<PaymentGroups> creategroup(@RequestParam String groupname) {
		System.out.println("Inside creategroup");
		payservice.createGroup(groupname);
		List<PaymentGroups> paymentgroups=payservice.getGroupNamesM();
		return paymentgroups;
	}
	
	@RequestMapping(value="/addGroupMember") 
	public @ResponseBody String addgroupmembers(@RequestParam String groupname,@RequestParam String personname) {
		System.out.println("Inside addgroupmembers");
		return payservice.addGroupMembers(groupname,personname);
	}
	
	@RequestMapping(value="/addPaymentInGroup") 
	public @ResponseBody List<Payments> addPaymentInGroup(@RequestParam String groupname,@RequestParam String personname,@RequestParam String remarks,@RequestParam String amount) {
		System.out.println("Inside addPaymentInGroup");
		payservice.addPaymentInGroup(groupname,personname,remarks,amount);
		List<Payments> payments = payservice.getPayments();
		return payments;
	}
	
	@RequestMapping(value="/seeGroupWiseExpense") 
	public @ResponseBody List<?> seeGroupWiseExpense(@RequestParam String groupname) {
		System.out.println("Inside seeGroupWiseExpense for group : " + groupname);
		return payservice.seeGroupWiseExpense(groupname);
	}

	@RequestMapping(value="/getGroupNames") 
	public @ResponseBody List<?> getGroupNamesM() {
		System.out.println("Inside getGroupNamesM");
		return payservice.getGroupNamesM();
	}

	@RequestMapping(value="/getPersonInThisGroup") 
	public @ResponseBody List<?> getPersonInThisGroup(@RequestParam String groupname) {
		System.out.println("Inside getPersonInThisGroup");
		return payservice.getPersonInThisGroup(groupname);
	}

	@RequestMapping(value="/addPaymentInGroupAndSharedExpense") 
	public @ResponseBody List<Payments> addPaymentInGroupAndSharedExpense(@RequestParam String groupname,@RequestParam String personname,
		   @RequestParam String remarks,@RequestParam String amount,@RequestParam String[]exppersonname,@RequestParam String[]expperson) {
		System.out.println("Inside addPaymentInGroupAndSharedExpense");
		
		System.out.println("groupname="+groupname+" personname="+personname+" amount="+amount+" remarks="+remarks);
		payservice.addPaymentInGroup(groupname,personname,remarks,amount);
		
		System.err.println("--------individual person----------");
		for(int i=0;i<exppersonname.length;i++) {
			System.out.println(i+" personname : " + exppersonname[i] +" amount : " + expperson[i]);
			payservice.addPaymentInGroupAndSharedExpense(exppersonname[i],expperson[i],groupname,remarks+"-"+i);
		}
		
		List<Payments> payments = payservice.getPayments();
		return payments;
	}
	
	@RequestMapping(value="/viewsharedexpense") 
	public @ResponseBody List<?> viewsharedexpense(@RequestParam String groupname) {
		System.out.println("Inside viewsharedexpense for group : " + groupname);
		return payservice.viewSharedExpense(groupname);
	}

}
