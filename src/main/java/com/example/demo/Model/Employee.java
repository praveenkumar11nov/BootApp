package com.example.demo.Model;

public class Employee {

	private String name;
	private String company;
	private String post;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public Employee(String name, String company, String post) {
		super();
		this.name = name;
		this.company = company;
		this.post = post;
	}
	
}
