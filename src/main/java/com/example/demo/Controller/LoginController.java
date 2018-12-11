package com.example.demo.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.example.demo.Model.Users;
import com.example.demo.Service.UserService;

@Controller
public class LoginController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value= {"/","/login"}, method ={RequestMethod.POST,RequestMethod.GET})
	public String getLoginPage() {
		System.out.println("inside getLoginPage......");
		return "login";
	}
	
	@RequestMapping(value="/home", method ={RequestMethod.POST,RequestMethod.GET})
	public String getHomePage(Authentication authentication,HttpServletRequest request) {
		System.out.println("inside getHomePage......");
		String user=authentication.getName();
		System.err.println("USERNAME ======== "+user);
		
		HttpSession session = request.getSession(false);
		session.setAttribute("username",user.toUpperCase());
		
		return "home";
	}
	
	public void readmethod(){
		/*
			Users USR=null;
			try {
				USR = userService.findById(1);
				System.out.println("DbUserName==========>"+USR.getName());
				System.out.println("DbPassWord==========>"+USR.getPwd());
				
				userService.findByNameAndPassword(user,pwd);
				userService.getUserFromDB(user, pwd);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if(user.equalsIgnoreCase(USR.getName())) {
				if(pwd.equalsIgnoreCase(USR.getPwd())) {
					return "home";
				}else {
					return "login";
				}
			}else {
				return "login";
			}*/
	}
}
