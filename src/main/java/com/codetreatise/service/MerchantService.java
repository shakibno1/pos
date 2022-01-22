package com.codetreatise.service;

import java.util.List;

import com.codetreatise.bean.Merchant;
import com.codetreatise.bean.ProductType;
import com.codetreatise.generic.GenericService;

public interface MerchantService extends GenericService<Merchant> {

	Merchant findByName(String name);

	Merchant findById(Long id);
	
	List<Merchant> findByProductType(ProductType productType);

}
