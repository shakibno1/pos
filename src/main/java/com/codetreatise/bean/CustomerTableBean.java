package com.codetreatise.bean;

public class CustomerTableBean {
	
	private String id;
	private String name;
	private String mobile;
	private String address;
	private String pcsAmount;
	private String balance;
	
	
	public CustomerTableBean(String id, String name, String mobile, String address, String pcsAmount, String balance) {
		super();
		this.id = id;
		this.name = name;
		this.mobile = mobile;
		this.address = address;
		this.pcsAmount = pcsAmount;
		this.balance = balance;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getPcsAmount() {
		return pcsAmount;
	}


	public void setPcsAmount(String pcsAmount) {
		this.pcsAmount = pcsAmount;
	}


	public String getBalance() {
		return balance;
	}


	public void setBalance(String balance) {
		this.balance = balance;
	}
	
	
}
