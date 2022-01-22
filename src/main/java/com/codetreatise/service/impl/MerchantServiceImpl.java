package com.codetreatise.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codetreatise.bean.Customer;
import com.codetreatise.bean.Merchant;
import com.codetreatise.bean.ProductType;
import com.codetreatise.repository.MerchantRepository;
import com.codetreatise.service.MerchantService;

@Service
public class MerchantServiceImpl implements MerchantService {

	@Autowired
	private MerchantRepository repository;

	@Override
	public Merchant save(Merchant entity) {
		return repository.save(entity);
	}

	@Override
	public Merchant update(Merchant entity) {
		return repository.save(entity);
	}

	@Override
	public void delete(Merchant entity) {
		repository.delete(entity);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	public List<Merchant> findAll() {
		return repository.findAll();
	}

	@Override
	public Merchant findByName(String Merchantname) {
		return repository.findByName(Merchantname);
	}

	@Override
	public void deleteInBatch(List<Merchant> Merchants) {
		repository.deleteInBatch(Merchants);
	}

	@Override
	public List<Merchant> findByProductType(ProductType productType) {
		return repository.findByProductType(productType);
	}

	@Override
	public Merchant find(Long id) {
		Optional<Merchant> merchantOptional = repository.findById(id);
		if (merchantOptional.isPresent()){
			Merchant merchant = merchantOptional.get();
			
			return merchant;
		}
		else{
		   return null;
		}
	}

	@Override
	public Merchant findById(Long id) {
		Optional<Merchant> merchantOptional = repository.findById(id);
		if (merchantOptional.isPresent()){
			Merchant merchant = merchantOptional.get();
			
			return merchant;
		}
		else{
		   return null;
		}
	}

}
