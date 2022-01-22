package com.codetreatise.bean;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="product_transaction_ledger")
public class ProductTransactionLedger extends BaseModel {
	
	private ProductStock productStock;
	private Invoice invoice;
	private ProductTransactionType productTransactionType;
	private CustomerPaymentStatus customerPaymentType;
	private Shop shop;
	
	@Enumerated (EnumType.STRING)
	public CustomerPaymentStatus getCustomerPaymentType() {
		return customerPaymentType;
	}

	public void setCustomerPaymentType(CustomerPaymentStatus customerPaymentType) {
		this.customerPaymentType = customerPaymentType;
	}
	
	@ManyToOne //(cascade=CascadeType.ALL)
	public ProductStock getProductStock() {
		return productStock;
	}
	public void setProductStock(ProductStock productStock) {
		this.productStock = productStock;
	}
	@ManyToOne (cascade=CascadeType.ALL)
	public Invoice getInvoice() {
		return invoice;
	}
	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}
	@Enumerated (EnumType.STRING)
	public ProductTransactionType getProductTransactionType() {
		return productTransactionType;
	}
	public void setProductTransactionType(ProductTransactionType productTransactionType) {
		this.productTransactionType = productTransactionType;
	}

	@ManyToOne
	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}
}
