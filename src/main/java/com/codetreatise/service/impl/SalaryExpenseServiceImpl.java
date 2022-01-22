package com.codetreatise.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codetreatise.bean.Customer;
import com.codetreatise.bean.Employee;
import com.codetreatise.bean.PayType;
import com.codetreatise.bean.SalaryExpense;
import com.codetreatise.bean.Shop;
import com.codetreatise.repository.SalaryExpenseRepository;
import com.codetreatise.service.SalaryExpenseService;

@Service
public class SalaryExpenseServiceImpl implements SalaryExpenseService {
	
	@Autowired
	private SalaryExpenseRepository repository;
	
	@Override
	public SalaryExpense save(SalaryExpense entity) {
		return repository.save(entity);
	}

	@Override
	public SalaryExpense update(SalaryExpense entity) {
		return repository.save(entity);
	}

	@Override
	public void delete(SalaryExpense entity) {
		repository.delete(entity);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	public List<SalaryExpense> findAll() {
		return repository.findAll();
	}

	@Override
	public void deleteInBatch(List<SalaryExpense> SalaryExpenses) {
		repository.deleteInBatch(SalaryExpenses);
	}

	@Override
	public SalaryExpense findByEmployeeAndPayType(Employee employee, PayType payType) {
		// TODO Auto-generated method stub
		return repository.findByEmployeeAndPayType(employee, payType);
	}

	@Override
	public List<SalaryExpense> findByPayType(PayType payType) {
		// TODO Auto-generated method stub
		return repository.findByPayType(payType);
	}

	@Override
	public List<SalaryExpense> findByEmployeeAndLedgerMonth(Employee employee, String ledgerMonth) {
		return repository.findByEmployeeAndLedgerMonth(employee, ledgerMonth);
	}

	@Override
	public Float getTotalSalaryExpenseFromDateRange(Shop shop, Timestamp from, Timestamp to) {
		// TODO Auto-generated method stub
		return repository.getTotalSalaryExpenseFromDateRange(shop, from, to);
	}

	@Override
	public Float getTotalSalaryExpenseBeforeFromDate(Shop shop, Timestamp from) {
		// TODO Auto-generated method stub
		return repository.getTotalSalaryExpenseBeforeFromDate(shop, from);
	}

	@Override
	public List<SalaryExpense> findByShopAndCreatedBetween(Shop shop, Timestamp from, Timestamp to) {
		// TODO Auto-generated method stub
		return repository.findByShopAndCreatedBetween(shop, from, to);
	}

	@Override
	public SalaryExpense find(Long id) {
		Optional<SalaryExpense> optional = repository.findById(id);
		if (optional.isPresent()){
			SalaryExpense salaryExpense = optional.get();
			
			return salaryExpense;
		}
		else{
		   return null;
		}
	}

	@Override
	public SalaryExpense findById(Long id) {
		Optional<SalaryExpense> optional = repository.findById(id);
		if (optional.isPresent()){
			SalaryExpense salaryExpense = optional.get();
			
			return salaryExpense;
		}
		else{
		   return null;
		}
	}
	
}
