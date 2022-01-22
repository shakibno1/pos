package com.codetreatise.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codetreatise.bean.PayType;
import com.codetreatise.repository.PayTypeRepository;
import com.codetreatise.service.PayTypeService;

@Service
public class PayTypeServiceImpl implements PayTypeService {

	@Autowired
	private PayTypeRepository repository;

	@Override
	public PayType save(PayType entity) {
		return repository.save(entity);
	}

	@Override
	public PayType update(PayType entity) {
		return repository.save(entity);
	}

	@Override
	public void delete(PayType entity) {
		repository.delete(entity);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	public List<PayType> findAll() {
		return repository.findAll();
	}

	@Override
	public void deleteInBatch(List<PayType> PayTypes) {
		repository.deleteInBatch(PayTypes);
	}

	@Override
	public PayType findByType(String type) {

		return repository.findByType(type);
	}

	@Override
	public PayType find(Long id) {
		Optional<PayType> payTypeOptional = repository.findById(id);
		if (payTypeOptional.isPresent()){
			PayType payType = payTypeOptional.get();
			
			return payType;
		}
		else{
		   return null;
		}
	}

	@Override
	public PayType findById(Long id) {
		Optional<PayType> payTypeOptional = repository.findById(id);
		if (payTypeOptional.isPresent()){
			PayType payType = payTypeOptional.get();
			
			return payType;
		}
		else{
		   return null;
		}
	}

}
