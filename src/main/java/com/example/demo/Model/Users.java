package com.example.demo.Model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="USERS")
//@Table(name = "USERS",schema="praveen")
public class Users {
	
	@Id
	@Column(name="ID")
	private Integer id;
	
	@Column(name="USERNAME")
	private String name;
	
	@Column(name="PASSWORD")
	private String pwd;
	
	@Column(name="ENABLED")
	private String status;
	
	@Column(name="CREATEDDATE")
	private Date createddate;
	
	@Column(name="CREATEDBY")
	private String createdby;

	@Column(name="EMAIL")
	private String email;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateddate() {
		return createddate;
	}

	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
