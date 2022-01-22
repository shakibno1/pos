package com.codetreatise.bean;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by Divineit-Iftekher on 1/8/2018.
 */
@Entity
@Table(name="employee")
public class Employee extends BaseModel{

    private String name;
    private String mobile;
    private String address;
    private Float salary;
    private Float previous_salary;
    private Boolean status;


    public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Float getPrevious_salary() {
		return previous_salary;
	}

	public void setPrevious_salary(Float previous_salary) {
		this.previous_salary = previous_salary;
	}

	private Shop shop;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Float getSalary() {
        return salary;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
    }

    @ManyToOne
    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
