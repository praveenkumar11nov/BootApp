package com.example.demo.Controller;

import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.example.demo.Service.UserService;

@Controller
public class LoginController {
	Logger log = Logger.getLogger("LoginController.java");
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/", method ={RequestMethod.POST,RequestMethod.GET})
	public String getLoginPage1() {
		return "redirect:/login";
	}
	
	@RequestMapping(value="/login", method ={RequestMethod.POST,RequestMethod.GET})
	public String getLoginPage2() {
		//readmethod();
		return "login";
	}
	
	@RequestMapping(value="/home", method ={RequestMethod.POST,RequestMethod.GET})
	public String getHomePage(Authentication authentication,HttpServletRequest request) {
		log.info("authentication.getName() ========== " + authentication.getName());
		log.info("authentication.getAuthorities() ========== " + authentication.getAuthorities());
		log.info("authentication.getClass() ========== " + authentication.getClass());
		log.info("authentication.getCredentials() ========== " + authentication.getCredentials());
		log.info("authentication.getDetails() ========== " + authentication.getDetails());
		log.info("authentication.getPrincipal() ========== " + authentication.getPrincipal());
		
		String user=authentication.getName();
		log.info("USERNAME ======== "+user);
		
		HttpSession session = request.getSession(false);
		session.setAttribute("username",user.toUpperCase());
		
		//return "home";
		return "redirect:/app/index";
	}
	
	@RequestMapping(value="/app/table", method ={RequestMethod.POST,RequestMethod.GET})
	public String goTodashboard() {
		log.info("Inside goTodashboard");
		return "ThemePages/Table";
	}
	
	@RequestMapping(value="/app/index", method ={RequestMethod.POST,RequestMethod.GET})
	public String goToIndex() {
		log.info("Inside goToIndex");
		return "ThemePages/Index";
	}
	
	@RequestMapping(value="/app/decor1", method ={RequestMethod.POST,RequestMethod.GET})
	public String goToManualDecor1() {
		log.info("Inside goToManualDecor1");
		return "ThemePages/JSP1";
	}
	
	@RequestMapping(value="/app/decor2", method ={RequestMethod.POST,RequestMethod.GET})
	public String goToManualDecor2() {
		log.info("Inside goToManualDecor2");
		return "ThemePages/NewFile";
	}
	
	public void readmethod(){
		
			try {
				userService.findByIdTesingPostgresSqlDataSource(1);
				userService.findByIdTesingMsqlDataSource(1);
				userService.findByIdTesingOracleDataSource(43);
			}
			catch (Exception e) {
				e.printStackTrace();
			}

	}
	
	//@Scheduled(cron = "0/1 * * * * ?")
	public void performTaskUsingCron() {
		System.out.println("Regular task performed using Cron at " + new Date());

	}
}
