package com.codetreatise.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codetreatise.bean.Customer;
import com.codetreatise.bean.CustomerBalance;
import com.codetreatise.bean.Shop;
import com.codetreatise.repository.CustomerBalanceRepository;
import com.codetreatise.service.CustomerBalanceService;

@Service
public class CustomerBalanceServiceImpl implements CustomerBalanceService {
	
	@Autowired
	private CustomerBalanceRepository repository;
	
	@Override
	public CustomerBalance save(CustomerBalance entity) {
		return repository.save(entity);
	}

	@Override
	public CustomerBalance update(CustomerBalance entity) {
		return repository.save(entity);
	}

	@Override
	public void delete(CustomerBalance entity) {
		repository.delete(entity);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}


	@Override
	public List<CustomerBalance> findAll() {
		return repository.findAll();
	}

	@Override
	public void deleteInBatch(List<CustomerBalance> customerBalance) {
		repository.deleteInBatch(customerBalance);
	}

	@Override
	public CustomerBalance findByCustomer(Customer customer) {
		return repository.findByCustomer(customer);
	}

	@Override
	public CustomerBalance findByShop(Shop shop) {
		return repository.findByShop(shop);
	}

	@Override
	public CustomerBalance findByCustomerAndShop(Customer customer, Shop shop) {
		return repository.findByCustomerAndShop(customer, shop);
	}

	@Override
	public Float getTotalCustomerBalanceUptoFromTimeStamp(Shop shop, Timestamp from) {
		// TODO Auto-generated method stub
		return repository.getTotalCustomerBalanceUptoFromTimeStamp(shop, from);
	}

	@Override
	public Float getTotalCustomerBalance(Shop shop, Timestamp from, Timestamp to) {
		// TODO Auto-generated method stub
		return repository.getTotalCustomerBalance(shop, from, to);
	}

	@Override
	public CustomerBalance find(Long id) {
		Optional<CustomerBalance> customeBalancerOptional = repository.findById(id);
		if (customeBalancerOptional.isPresent()){
			CustomerBalance customerBalance = customeBalancerOptional.get();
			
			return customerBalance;
		}
		else{
		   return null;
		}
	}

	@Override
	public CustomerBalance findByCustomerAndShopAndAmountNot(Customer customer, Shop shop, Float amount) {
		// TODO Auto-generated method stub
		return repository.findByCustomerAndShopAndAmountNot(customer, shop, amount);
	}
	
}
