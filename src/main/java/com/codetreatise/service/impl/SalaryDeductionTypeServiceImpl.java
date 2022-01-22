package com.codetreatise.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codetreatise.bean.Customer;
import com.codetreatise.bean.SalaryDeductionType;
import com.codetreatise.repository.SalaryDeductionTypeRepository;
import com.codetreatise.service.SalaryDeductionTypeService;

@Service
public class SalaryDeductionTypeServiceImpl implements SalaryDeductionTypeService {
	
	@Autowired
	private SalaryDeductionTypeRepository repository;
	
	@Override
	public SalaryDeductionType save(SalaryDeductionType entity) {
		return repository.save(entity);
	}

	@Override
	public SalaryDeductionType update(SalaryDeductionType entity) {
		return repository.save(entity);
	}

	@Override
	public void delete(SalaryDeductionType entity) {
		repository.delete(entity);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	public List<SalaryDeductionType> findAll() {
		return repository.findAll();
	}

	@Override
	public void deleteInBatch(List<SalaryDeductionType> SalaryDeductionTypes) {
		repository.deleteInBatch(SalaryDeductionTypes);
	}

	@Override
	public SalaryDeductionType findByType(String type) {
		
		return repository.findByType(type);
	}

	@Override
	public SalaryDeductionType find(Long id) {
		Optional<SalaryDeductionType> salaryDeductionTypeOptional = repository.findById(id);
		if (salaryDeductionTypeOptional.isPresent()){
			SalaryDeductionType salaryDeductionType = salaryDeductionTypeOptional.get();
			
			return salaryDeductionType;
		}
		else{
		   return null;
		}
	}

	@Override
	public SalaryDeductionType findById(Long id) {
		Optional<SalaryDeductionType> salaryDeductionTypeOptional = repository.findById(id);
		if (salaryDeductionTypeOptional.isPresent()){
			SalaryDeductionType salaryDeductionType = salaryDeductionTypeOptional.get();
			
			return salaryDeductionType;
		}
		else{
		   return null;
		}
	}
	
}
