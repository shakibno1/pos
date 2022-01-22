package com.codetreatise.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codetreatise.bean.Customer;
import com.codetreatise.bean.Employee;
import com.codetreatise.bean.Shop;
import com.codetreatise.repository.EmployeeRepository;
import com.codetreatise.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	private EmployeeRepository repository;
	
	@Override
	public Employee save(Employee entity) {
		return repository.save(entity);
	}

	@Override
	public Employee update(Employee entity) {
		return repository.save(entity);
	}

	@Override
	public void delete(Employee entity) {
		repository.delete(entity);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}


	@Override
	public List<Employee> findAll() {
		return repository.findAll();
	}

	@Override
	public Employee findByName(String Employeename) {
		return repository.findByName(Employeename);
	}

	@Override
	public void deleteInBatch(List<Employee> Employees) {
		repository.deleteInBatch(Employees);
	}

	@Override
	public List<Employee> findByShop(Shop shop) {
		return repository.findByShop(shop);
	}

	@Override
	public List<Employee> findByShopAndStatus(Shop shop, Boolean status) {
		return repository.findByShopAndStatus(shop,true);
	}

	@Override
	public Employee find(Long id) {
		Optional<Employee> employeeOptional = repository.findById(id);
		if (employeeOptional.isPresent()){
			Employee employee = employeeOptional.get();
			
			return employee;
		}
		else{
		   return null;
		}
	}

	@Override
	public Employee findById(Long id) {
		Optional<Employee> employeeOptional = repository.findById(id);
		if (employeeOptional.isPresent()){
			Employee employee = employeeOptional.get();
			
			return employee;
		}
		else{
		   return null;
		}
	}
	
}
