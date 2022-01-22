package com.codetreatise.service;

import java.sql.Timestamp;
import java.util.List;

import com.codetreatise.bean.Customer;
import com.codetreatise.generic.GenericService;


public interface CustomerService extends GenericService<Customer> {

	Customer findByName(String name);

	Customer findByMobile(String mobile);

	Customer findById(Long id);
	
//	List<Customer> findByNameLikeAndMobileAndCreatedGreaterThanEqualAndCreatedLessThanEqual (String name, String mobile, Timestamp from , Timestamp to );

}
