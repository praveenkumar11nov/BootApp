package com.example.demo.Controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.Employee;

@RestController
@RequestMapping(value="/webapi")
public class WebServiceController {
	/*
	@RequestMapping(value="/empdetails1")
	public String getEmployeeDeatils1() throws JSONException{
		JSONObject json=new JSONObject();
		try{
			json.put("NAME","RAM");
			json.put("COMPANY","BCITS");
			json.put("POST","DEVELOPER");
		}catch(Exception e){
			json.put("ERROR","Exception While Showing Details");
			e.printStackTrace();
		}
		return json.toString();
	}*/
	
	@RequestMapping(value="/empdetails")
	public List<Employee> getEmployeeDeatils() throws JSONException{
		List<Employee> list=new ArrayList<>();
		list.add(new Employee("RAM","BCITS","DEVELOPER"));
		list.add(new Employee("ANJUM","BCITS","DEVELOPER"));
		list.add(new Employee("SACHIN","BCITS","DEVELOPER"));
		list.add(new Employee("VEMA","BCITS","DEVELOPER"));
		return list;
	}

}
