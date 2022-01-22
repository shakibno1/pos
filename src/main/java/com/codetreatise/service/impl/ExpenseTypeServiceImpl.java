package com.codetreatise.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codetreatise.bean.Customer;
import com.codetreatise.bean.ExpenseType;
import com.codetreatise.repository.ExpenseTypeRepository;
import com.codetreatise.service.ExpenseTypeService;

@Service
public class ExpenseTypeServiceImpl implements ExpenseTypeService {

	@Autowired
	private ExpenseTypeRepository repository;

	@Override
	public ExpenseType save(ExpenseType entity) {
		return repository.save(entity);
	}

	@Override
	public ExpenseType update(ExpenseType entity) {
		return repository.save(entity);
	}

	@Override
	public void delete(ExpenseType entity) {
		repository.delete(entity);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	public List<ExpenseType> findAll() {
		return repository.findAll();
	}

	@Override
	public void deleteInBatch(List<ExpenseType> ExpenseTypes) {
		repository.deleteInBatch(ExpenseTypes);
	}

	@Override
	public ExpenseType findByType(String type) {
		return repository.findByType(type);
	}

	@Override
	public ExpenseType find(Long id) {
		Optional<ExpenseType> expenseTypeOptional = repository.findById(id);
		if (expenseTypeOptional.isPresent()){
			ExpenseType expenseType = expenseTypeOptional.get();
			
			return expenseType;
		}
		else{
		   return null;
		}
	}

	@Override
	public ExpenseType findById(Long id) {
		Optional<ExpenseType> expenseTypeOptional = repository.findById(id);
		if (expenseTypeOptional.isPresent()){
			ExpenseType expenseType = expenseTypeOptional.get();
			
			return expenseType;
		}
		else{
		   return null;
		}
	}

}
