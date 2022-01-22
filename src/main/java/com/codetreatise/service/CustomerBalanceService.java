package com.codetreatise.service;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.codetreatise.bean.Customer;
import com.codetreatise.bean.CustomerBalance;
import com.codetreatise.bean.Shop;
import com.codetreatise.generic.GenericService;

public interface CustomerBalanceService extends GenericService<CustomerBalance> {

	CustomerBalance findByCustomer(Customer customer);

	CustomerBalance findByShop(Shop shop);

	CustomerBalance findByCustomerAndShop(Customer customer, Shop shop);
	
	CustomerBalance findByCustomerAndShopAndAmountNot(Customer customer, Shop shop , Float amount);
	
	Float getTotalCustomerBalanceUptoFromTimeStamp(Shop shop  , Timestamp from);
	
	Float getTotalCustomerBalance(Shop shop  , Timestamp from ,  Timestamp to);

}
