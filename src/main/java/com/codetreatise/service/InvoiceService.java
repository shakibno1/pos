package com.codetreatise.service;

import java.sql.Timestamp;
import java.util.List;

import com.codetreatise.bean.Customer;
import com.codetreatise.bean.Invoice;
import com.codetreatise.bean.Shop;
import com.codetreatise.generic.GenericService;

public interface InvoiceService extends GenericService<Invoice> {

	List<Invoice> findByCustomer(Customer customer);

	Invoice findByShop(Shop shop);

	Invoice findByCustomerAndShop(Customer customer, Shop shop);

//	Float getTotalCustomerBalance(Timestamp from, Timestamp to, Shop shop);
//	
//	Float getTotalCustomerBalanceUptoFromTimeStamp(Timestamp from ,Shop shop );
}
