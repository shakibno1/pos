package com.codetreatise.bean;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="salary_deduction")
public class SalaryDeduction extends BaseModel {

	private Float amount;
	private Employee employee;
	private SalaryDeductionType salaryDeductionType;
	private Shop shop;
	private String ledgerMonth;
	
	public Float getAmount() {
		return amount;
	}
	public void setAmount(Float amount) {
		this.amount = amount;
	}
	@ManyToOne
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	@ManyToOne
	public SalaryDeductionType getSalaryDeductionType() {
		return salaryDeductionType;
	}
	public void setSalaryDeductionType(SalaryDeductionType salaryDeductionType) {
		this.salaryDeductionType = salaryDeductionType;
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
