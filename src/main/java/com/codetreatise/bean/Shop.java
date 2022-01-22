package com.codetreatise.bean;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;


@Entity
public class Shop extends BaseModel {

	private String name;
	private String address;
	private String mobile;
	
	private List<Employee> employees = new ArrayList<Employee>();


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


    @OneToMany(mappedBy = "shop")
    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

	@Override
	public String toString() {
		return "User [id=" + id + "]";
	}

	
}
