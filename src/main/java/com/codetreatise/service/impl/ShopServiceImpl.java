package com.codetreatise.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codetreatise.bean.Customer;
import com.codetreatise.bean.Shop;
import com.codetreatise.repository.ShopRepository;
import com.codetreatise.service.ShopService;

@Service
public class ShopServiceImpl implements ShopService {
	
	@Autowired
	private ShopRepository repository;
	
	@Override
	public Shop save(Shop entity) {
		return repository.save(entity);
	}

	@Override
	public Shop update(Shop entity) {
		return repository.save(entity);
	}

	@Override
	public void delete(Shop entity) {
		repository.delete(entity);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	public List<Shop> findAll() {
		return repository.findAll();
	}

	@Override
	public Shop findByName(String Shopname) {
		return repository.findByName(Shopname);
	}

	@Override
	public void deleteInBatch(List<Shop> Shops) {
		repository.deleteInBatch(Shops);
	}

	@Override
	public Shop find(Long id) {
		Optional<Shop> shopOptional = repository.findById(id);
		if (shopOptional.isPresent()){
			Shop shop = shopOptional.get();
			
			return shop;
		}
		else{
		   return null;
		}
	}
	
}
