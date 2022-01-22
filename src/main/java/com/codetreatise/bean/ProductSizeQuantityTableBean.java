package com.codetreatise.bean;

public class ProductSizeQuantityTableBean {
	
	private String size;
	private String quantity;
	
	public ProductSizeQuantityTableBean(String quantity, String size) {
		this.size = size;
		this.quantity = quantity;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	
	
	
}
