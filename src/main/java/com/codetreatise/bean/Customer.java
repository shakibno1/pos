package com.codetreatise.bean;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="customer")
public class Customer extends BaseModel{
	
	private String name;
	private String mobile;
	private String address;
	private String membership_status;
	
	private List<Invoice> invoice;
	
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
	public String getMembership_status() {
		return membership_status;
	}
	public void setMembership_status(String membership_status) {
		this.membership_status = membership_status;
	}
	@OneToMany (cascade={CascadeType.MERGE ,CascadeType.PERSIST} , mappedBy="customer")
	public List<Invoice> getInvoice() {
		return invoice;
	}
	public void setInvoice(List<Invoice> invoice) {
		this.invoice = invoice;
	}
	
	
	
}
