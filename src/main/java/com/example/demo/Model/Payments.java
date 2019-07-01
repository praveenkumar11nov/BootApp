package com.example.demo.Model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@NamedQueries({
	@NamedQuery(name="Payments.getAllPayments",query="SELECT p FROM Payments p WHERE p.groupname LIKE :grpname ORDER BY p.id DESC")		
})

@Entity
@Table(name = "PAYMENTS",schema = "bootapp")
public class Payments {
	
	@Id
	@Column(name="ID")
	private Integer id;
	
	@Column(name="PERSONNAME")
	private String personname;
	
	@Column(name="GROUPNAME")
	private String	groupname;
	
	@Column(name="PAYDATE")
	private Date paydate;
	
	@Column(name="REMARKS")
	private String remarks;
	
	@Column(name="AMOUNT")
	private String amount;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPersonname() {
		return personname;
	}

	public void setPersonname(String personname) {
		this.personname = personname;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public Date getPaydate() {
		return paydate;
	}

	public void setPaydate(Date paydate) {
		this.paydate = paydate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
