package com.codetreatise.bean;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="salary_expense")
public class SalaryExpense extends BaseModel {

	
	private Float amount;
	private PayType payType;
	private Shop shop;
	private String ledgerMonth;
	
	private Employee employee;
	
	@ManyToOne
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public Float getAmount() {
		return amount;
	}
	public void setAmount(Float amount) {
		this.amount = amount;
	}
	@ManyToOne
	public PayType getPayType() {
		return payType;
	}
	public void setPayType(PayType payType) {
		this.payType = payType;
	}
	@ManyToOne
	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}
	public String getLedgerMonth() {
		return ledgerMonth;
	}
	public void setLedgerMonth(String ledgerMonth) {
		this.ledgerMonth = ledgerMonth;
	}
	
	
		
	
}
