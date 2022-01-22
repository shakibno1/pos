package com.codetreatise.service;

import java.util.List;

import com.codetreatise.bean.Employee;
import com.codetreatise.bean.Shop;
import com.codetreatise.generic.GenericService;

public interface EmployeeService extends GenericService<Employee> {
	
	Employee findByName(String name);
	
	Employee findById(Long id);
	
	List<Employee> findByShop (Shop shop);

	List<Employee> findByShopAndStatus (Shop shop,Boolean status);
	
	
}
