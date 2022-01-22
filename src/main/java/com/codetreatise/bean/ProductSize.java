package com.codetreatise.bean;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="product_size")
public class ProductSize extends BaseModel {
	
	private String size;
	
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	
	
}
