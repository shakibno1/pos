package com.codetreatise.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codetreatise.bean.Customer;
import com.codetreatise.bean.ProductStockParent;
import com.codetreatise.repository.ProductStockParentRepository;
import com.codetreatise.service.ProductStockParentService;

@Service
public class ProductStockParentServiceImpl implements ProductStockParentService {
	
	@Autowired
	private ProductStockParentRepository repository;
	
	@Override
	public ProductStockParent save(ProductStockParent entity) {
		return repository.save(entity);
	}

	@Override
	public ProductStockParent update(ProductStockParent entity) {
		return repository.save(entity);
	}

	@Override
	public void delete(ProductStockParent entity) {
		repository.delete(entity);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	public List<ProductStockParent> findAll() {
		return repository.findAll();
	}

	@Override
	public void deleteInBatch(List<ProductStockParent> ProductQuantitySizes) {
		repository.deleteInBatch(ProductQuantitySizes);
	}

	@Override
	public ProductStockParent find(Long id) {
		Optional<ProductStockParent> productStockParentOptional = repository.findById(id);
		if (productStockParentOptional.isPresent()){
			ProductStockParent productStockParent = productStockParentOptional.get();
			
			return productStockParent;
		}
		else{
		   return null;
		}
	}

	@Override
	public ProductStockParent findById(Long id) {
		Optional<ProductStockParent> productStockParentOptional = repository.findById(id);
		if (productStockParentOptional.isPresent()){
			ProductStockParent productStockParent = productStockParentOptional.get();
			
			return productStockParent;
		}
		else{
		   return null;
		}
	}
	
}
