package com.example.demo.Controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.example.demo.Model.Users;
import com.example.demo.Service.UserService;

@Controller
public class LoginController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/", method ={RequestMethod.POST,RequestMethod.GET})
	public String getLoginPage() {
		System.out.println("inside getLoginPage......");
		return "login.jsp";
	}
	
	@RequestMapping(value="/home", method ={RequestMethod.POST,RequestMethod.GET})
	public String getHomePage(HttpServletRequest request) {
		System.out.println("inside getHomePage......");
		String user=request.getParameter("username");
		String pwd=request.getParameter("password");
		System.err.println("username="+user+" password="+pwd);
		
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
				return "home.jsp";
			}else {
				return "login.jsp";
			}
		}else {
			return "login.jsp";
		}
		
	}

}
