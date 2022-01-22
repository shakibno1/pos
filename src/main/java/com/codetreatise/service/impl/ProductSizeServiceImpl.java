package com.codetreatise.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codetreatise.bean.Customer;
import com.codetreatise.bean.ProductSize;
import com.codetreatise.repository.ProductSizeRepository;
import com.codetreatise.service.ProductSizeService;

@Service
public class ProductSizeServiceImpl implements ProductSizeService {
	
	@Autowired
	private ProductSizeRepository repository;
	
	@Override
	public ProductSize save(ProductSize entity) {
		return repository.save(entity);
	}

	@Override
	public ProductSize update(ProductSize entity) {
		return repository.save(entity);
	}

	@Override
	public void delete(ProductSize entity) {
		repository.delete(entity);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	public List<ProductSize> findAll() {
		return repository.findAll();
	}

	@Override
	public void deleteInBatch(List<ProductSize> ProductQuantitySizes) {
		repository.deleteInBatch(ProductQuantitySizes);
	}

	@Override
	public ProductSize findBySize(String size) {
		return repository.findBySize(size);
	}

	@Override
	public ProductSize find(Long id) {
		Optional<ProductSize> productSizeOptional = repository.findById(id);
		if (productSizeOptional.isPresent()){
			ProductSize productSize = productSizeOptional.get();
			
			return productSize;
		}
		else{
		   return null;
		}
	}

	@Override
	public ProductSize findById(Long id) {
		Optional<ProductSize> productSizeOptional = repository.findById(id);
		if (productSizeOptional.isPresent()){
			ProductSize productSize = productSizeOptional.get();
			
			return productSize;
		}
		else{
		   return null;
		}
	}
	
}
