package com.codetreatise.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codetreatise.bean.Customer;
import com.codetreatise.bean.Employee;
import com.codetreatise.bean.SalaryDeduction;
import com.codetreatise.bean.SalaryDeductionType;
import com.codetreatise.bean.Shop;
import com.codetreatise.repository.SalaryDeductionRepository;
import com.codetreatise.service.SalaryDeductionService;

@Service
public class SalaryDeductionServiceImpl implements SalaryDeductionService {
	
	@Autowired
	private SalaryDeductionRepository repository;
	
	@Override
	public SalaryDeduction save(SalaryDeduction entity) {
		return repository.save(entity);
	}

	@Override
	public SalaryDeduction update(SalaryDeduction entity) {
		return repository.save(entity);
	}

	@Override
	public void delete(SalaryDeduction entity) {
		repository.delete(entity);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	public List<SalaryDeduction> findAll() {
		return repository.findAll();
	}

	@Override
	public void deleteInBatch(List<SalaryDeduction> SalaryDeductions) {
		repository.deleteInBatch(SalaryDeductions);
	}

	@Override
	public SalaryDeduction findByShop(Shop shop) {
		// TODO Auto-generated method stub
		return repository.findByShop(shop);
	}

//	@Override
//	public SalaryDeduction findByShopAndEmployeeAndLedgerMonth(Shop shop, Employee employee, String ledgerMonth) {
//		// TODO Auto-generated method stub
//		return repository.findByShopAndEmployeeAndLedgerMonth(shop, employee, ledgerMonth);
//	}

	@Override
	public SalaryDeduction findByShopAndEmployee(Shop shop, Employee employee) {
		// TODO Auto-generated method stub
		return repository.findByShopAndEmployee(shop, employee);
	}

	@Override
	public List<SalaryDeduction> findBySalaryDeductionType(SalaryDeductionType salaryDeductionType) {
		// TODO Auto-generated method stub
		return repository.findBySalaryDeductionType(salaryDeductionType);
	}

	@Override
	public List<SalaryDeduction> findByEmployeeAndLedgerMonth(Employee employee, String ledgerMonth) {
		// TODO Auto-generated method stub
		return repository.findByEmployeeAndLedgerMonth(employee, ledgerMonth);
	}

	@Override
	public SalaryDeduction find(Long id) {
		Optional<SalaryDeduction> salaryDeductionOptional = repository.findById(id);
		if (salaryDeductionOptional.isPresent()){
			SalaryDeduction salaryDeduction = salaryDeductionOptional.get();
			
			return salaryDeduction;
		}
		else{
		   return null;
		}
	}

	@Override
	public SalaryDeduction findById(Long id) {
		Optional<SalaryDeduction> salaryDeductionOptional = repository.findById(id);
		if (salaryDeductionOptional.isPresent()){
			SalaryDeduction salaryDeduction = salaryDeductionOptional.get();
			
			return salaryDeduction;
		}
		else{
		   return null;
		}
	}
	
}
