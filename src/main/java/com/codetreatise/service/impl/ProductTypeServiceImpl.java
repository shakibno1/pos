package com.codetreatise.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codetreatise.bean.Customer;
import com.codetreatise.bean.ProductType;
import com.codetreatise.repository.ProductTypeRepository;
import com.codetreatise.service.ProductTypeService;

@Service
public class ProductTypeServiceImpl implements ProductTypeService {
	
	@Autowired
	private ProductTypeRepository repository;
	
	@Override
	public ProductType save(ProductType entity) {
		return repository.save(entity);
	}

	@Override
	public ProductType update(ProductType entity) {
		return repository.save(entity);
	}

	@Override
	public void delete(ProductType entity) {
		repository.delete(entity);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	public List<ProductType> findAll() {
		return repository.findAll();
	}

	@Override
	public void deleteInBatch(List<ProductType> ProductTypes) {
		repository.deleteInBatch(ProductTypes);
	}

	@Override
	public ProductType findByType(String type) {
		// TODO Auto-generated method stub
		return repository.findByType(type);
	}

	@Override
	public ProductType find(Long id) {
		Optional<ProductType> productTypeOptional = repository.findById(id);
		if (productTypeOptional.isPresent()){
			ProductType productType = productTypeOptional.get();
			
			return productType;
		}
		else{
		   return null;
		}
	}

	@Override
	public ProductType findById(Long id) {
		Optional<ProductType> productTypeOptional = repository.findById(id);
		if (productTypeOptional.isPresent()){
			ProductType productType = productTypeOptional.get();
			
			return productType;
		}
		else{
		   return null;
		}
	}
	
}
