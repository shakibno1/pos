package com.codetreatise.bean;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="merchant")
public class Merchant extends BaseModel {
	
	private String name;
	private String code;
	private String phone;
	private String address;
	
	private ProductType productType;
	
//	@OneToMany
//	private List<ProductStock> productStocks = new ArrayList<ProductStock>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@ManyToOne
	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}
	
//	@OneToMany(mappedBy = "merchant")
//	public List<ProductStock> getProductStocks() {
//		return productStocks;
//	}
//
//	public void setProductStocks(List<ProductStock> productStocks) {
//		this.productStocks = productStocks;
//	}
	

}
