package com.codetreatise.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codetreatise.bean.Customer;
import com.codetreatise.repository.CustomerRepository;
import com.codetreatise.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository repository;

	@Transactional  (rollbackFor = Exception.class ,  propagation = Propagation.REQUIRED)
	@Override
	public Customer save(Customer entity) {
		return repository.save(entity);
	}
	
	@Transactional (rollbackFor = Exception.class ,  propagation = Propagation.REQUIRED)
	@Override
	public Customer update(Customer entity) {
		return repository.save(entity);
	}

	@Override
	public void delete(Customer entity) {
		repository.delete(entity);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	public List<Customer> findAll() {
		return repository.findAll();
	}

	@Override
	public Customer findByName(String Customername) {
		return repository.findByName(Customername);
	}

	@Override
	public void deleteInBatch(List<Customer> Customers) {
		repository.deleteInBatch(Customers);
	}

//	@Override
//	public Customer findById(Long id) {
//		return repository.findById(id);
//	}

	@Override
	public Customer findByMobile(String mobile) {
		
		return repository.findByMobile(mobile);
	}

	@Override
	public Customer find(Long id) {
		// TODO Auto-generated method stub
		
		Optional<Customer> customerOptional = repository.findById(id);
		if (customerOptional.isPresent()){
			Customer customer = customerOptional.get();
			
			return customer;
		}
		else{
		   return null;
		}
	}

	@Override
	public Customer findById(Long id) {
		Optional<Customer> customerOptional = repository.findById(id);
		if (customerOptional.isPresent()){
			Customer customer = customerOptional.get();
			
			return customer;
		}
		else{
		   return null;
		}
	}

//	@Override
//	public List<Customer> findByNameLikeAndMobileAndCreatedGreaterThanEqualAndCreatedLessThanEqual(String name,
//			String mobile, Timestamp from, Timestamp to) {
//		// TODO Auto-generated method stub
//		return repository.findByNameLikeAndMobileAndCreatedGreaterThanEqualAndCreatedLessThanEqual(name, mobile, from, to);
//	}

}
