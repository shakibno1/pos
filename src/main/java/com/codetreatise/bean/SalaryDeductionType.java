package com.codetreatise.bean;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="salary_deduction_type")
public class SalaryDeductionType extends BaseModel {

	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
