package com.codetreatise.bean;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "product_stock")
public class ProductStock {

	
	protected Long id;
	
	@Id
	@Column (nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	private Integer quantity;
	
	@Column (nullable = false)
	private Float priceBuying;
	
	@Column (nullable = false)
	private Float priceSelling;
	
	private Float discount;
	
	private String productDescription;
	
	@Enumerated (EnumType.STRING)
	private ProductStockStatus productStockStatus;
	
	@Column (nullable = false)
	private Shop shop;
	
	@Column (nullable = false)
	private Merchant merchant;
	
	@Column (nullable = false)
	private ProductType productType;
	
	@Column (nullable = false)
	private ProductStockParent productStockParent; 
	
	private ProductSize productSize; 
	
	@Enumerated (EnumType.STRING)
	private CustomerPaymentStatus customerPaymentType;
	
	private Invoice invoice;

	private String createdBy;
	private String updatedBy;
	private Timestamp created;
	private Timestamp updated;
	
	@Column(nullable = true)
	private Integer stockRealization;
	@Column(nullable = true)
	private Employee employee;

	@ManyToOne
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public Timestamp getUpdated() {
		return updated;
	}

	public void setUpdated(Timestamp updated) {
		this.updated = updated;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@ManyToOne //(cascade=CascadeType.ALL)
	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	@ManyToOne //(fetch=FetchType.LAZY)    //(cascade=CascadeType.ALL) 
	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

	@ManyToOne //(cascade=CascadeType.ALL)
	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}
	
	@ManyToOne //(cascade=CascadeType.ALL)
	public ProductStockParent getProductStockParent() {
		return productStockParent;
	}

	public void setProductStockParent(ProductStockParent productStockParent) {
		this.productStockParent = productStockParent;
	}
	@Enumerated (EnumType.STRING)
	public ProductStockStatus getProductStockStatus() {
		return productStockStatus;
	}

	public void setProductStockStatus(ProductStockStatus productStockStatus) {
		this.productStockStatus = productStockStatus;
	}
	
	@ManyToOne //(fetch=FetchType.LAZY) //(cascade=CascadeType.ALL)
	public ProductSize getProductSize() {
		return productSize;
	}

	public void setProductSize(ProductSize productSize) {
		this.productSize = productSize;
	}

	public Float getDiscount() {
		return discount;
	}

	public void setDiscount(Float discount) {
		this.discount = discount;
	}
	
	@ManyToOne  //(cascade=CascadeType.ALL)
	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}
//	@ManyToOne (cascade=CascadeType.ALL)
//	public Customer getCustomer() {
//		return customer;
//	}
//
//	public void setCustomer(Customer customer) {
//		this.customer = customer;
//	}
	@Enumerated (EnumType.STRING)
	public CustomerPaymentStatus getCustomerPaymentType() {
		return customerPaymentType;
	}
	
	

	public void setCustomerPaymentType(CustomerPaymentStatus customerPaymentType) {
		this.customerPaymentType = customerPaymentType;
	}

	public Float getPriceBuying() {
		return priceBuying;
	}

	public void setPriceBuying(Float priceBuying) {
		this.priceBuying = priceBuying;
	}

	public Float getPriceSelling() {
		return priceSelling;
	}

	public void setPriceSelling(Float priceSelling) {
		this.priceSelling = priceSelling;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public Integer getStockRealization() {
		return stockRealization;
	}

	public void setStockRealization(Integer stockRealization) {
		this.stockRealization = stockRealization;
	}
	

}
