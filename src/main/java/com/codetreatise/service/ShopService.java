package com.codetreatise.service;

import com.codetreatise.bean.Shop;
import com.codetreatise.generic.GenericService;

public interface ShopService extends GenericService<Shop> {
	
	Shop findByName(String name);
	
}
