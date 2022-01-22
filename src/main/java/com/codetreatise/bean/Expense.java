package com.codetreatise.bean;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="expense")
public class Expense extends BaseModel {

	private Float amount;
	private Shop shop;
	private ExpenseType expenseType;
	private Merchant merchant;
	private Timestamp expenseDate;
	public Timestamp getExpenseDate() {
		return expenseDate;
	}
	public void setExpenseDate(Timestamp expenseDate) {
		this.expenseDate = expenseDate;
	}
	public Float getAmount() {
		return amount;
	}
	public void setAmount(Float amount) {
		this.amount = amount;
	}
	@ManyToOne
	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}
	@ManyToOne
	public ExpenseType getExpenseType() {
		return expenseType;
	}
	public void setExpenseType(ExpenseType expenseType) {
		this.expenseType = expenseType;
	}
	@ManyToOne
	public Merchant getMerchant() {
		return merchant;
	}
	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}
	
	
	
}
