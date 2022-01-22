package com.codetreatise.service.impl;

import com.codetreatise.bean.Customer;
import com.codetreatise.bean.PaymentInfo;
import com.codetreatise.bean.Shop;
import com.codetreatise.repository.CustomerRepository;
import com.codetreatise.repository.PaymentInfoRepository;
import com.codetreatise.service.CustomerService;
import com.codetreatise.service.PaymentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentInfoServiceImpl implements PaymentInfoService {

	@Autowired
	private PaymentInfoRepository repository;

	@Transactional  (rollbackFor = Exception.class ,  propagation = Propagation.REQUIRED)
	@Override
	public PaymentInfo save(PaymentInfo entity) {
		return repository.save(entity);
	}
	
	@Transactional (rollbackFor = Exception.class ,  propagation = Propagation.REQUIRED)
	@Override
	public PaymentInfo update(PaymentInfo entity) {
		return repository.save(entity);
	}

	@Override
	public void delete(PaymentInfo entity) {
		repository.delete(entity);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	public List<PaymentInfo> findAll() {
		return repository.findAll();
	}

	@Override
	public PaymentInfo findByShopAndYearAndMonth(Shop shop, String year,String month) {
		return repository.findByShopAndYearAndMonth(shop,year,month);
	}

	@Override
	public List<PaymentInfo> findByShopAndYearAndMonthAndPaymentStatus(Shop shop, String year, String month, Boolean paymentStatus) {
		return repository.findByShopAndYearAndMonthAndPaymentStatus(shop,year,month,paymentStatus);
	}

	@Override
	public List<PaymentInfo> findByShopAndPaymentStatus(Shop shop, Boolean paymentStatus) {
		return repository.findByShopAndPaymentStatus(shop,paymentStatus);
	}

	@Override
	public void deleteInBatch(List<PaymentInfo> PaymentInfos) {
		repository.deleteInBatch(PaymentInfos);
	}


	@Override
	public PaymentInfo find(Long id) {
		// TODO Auto-generated method stub
		
		Optional<PaymentInfo> paymentInfoOptional = repository.findById(id);
		if (paymentInfoOptional.isPresent()){
			PaymentInfo paymentInfo = paymentInfoOptional.get();
			
			return paymentInfo;
		}
		else{
		   return null;
		}
	}

	@Override
	public PaymentInfo findById(Long id) {
		Optional<PaymentInfo> paymentInfoOptional = repository.findById(id);
		if (paymentInfoOptional.isPresent()){
			PaymentInfo paymentInfo = paymentInfoOptional.get();
			
			return paymentInfo;
		}
		else{
		   return null;
		}
	}

}
