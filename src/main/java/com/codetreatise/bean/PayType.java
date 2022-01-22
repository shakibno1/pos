package com.codetreatise.bean;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="pay_type")
public class PayType extends BaseModel {

	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
