package com.codetreatise.bean;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "invoice")
public class Invoice extends BaseModel {

	private Customer customer;
	private Shop shop;
	
	private List<ProductStock> productStocks;
	
	private List<ProductTransactionLedger> productTransactionLedgers;

	@ManyToOne //(cascade=CascadeType.ALL)
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@ManyToOne
	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}
	
	@OneToMany (cascade={CascadeType.MERGE ,CascadeType.PERSIST} , mappedBy="invoice")
    public List<ProductStock> getProductStocks() {
        return productStocks;
    }

    public void setProductStocks(List<ProductStock> stocks) {
        this.productStocks = stocks;
    }
    
    @OneToMany (cascade=CascadeType.ALL , mappedBy="invoice")
	public List<ProductTransactionLedger> getProductTransactionLedgers() {
		return productTransactionLedgers;
	}

	public void setProductTransactionLedgers(List<ProductTransactionLedger> productTransactionLedgers) {
		this.productTransactionLedgers = productTransactionLedgers;
	}
    
    

}
