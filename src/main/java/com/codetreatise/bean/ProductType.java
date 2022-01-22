package com.codetreatise.bean;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="product_type")
public class ProductType extends BaseModel {
	
	private String type;
	
//	@OneToMany
//	private List<ProductStock> productStocks = new ArrayList<ProductStock>();

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
//	@OneToMany(mappedBy = "product_stock")
//	public List<ProductStock> getProductStocks() {
//		return productStocks;
//	}
//
//	public void setProductStocks(List<ProductStock> productStocks) {
//		this.productStocks = productStocks;
//	}
}
