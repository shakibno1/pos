package com.codetreatise.service;

import com.codetreatise.bean.ProductSize;
import com.codetreatise.generic.GenericService;

public interface ProductSizeService extends GenericService<ProductSize> {

	ProductSize findById(Long id);
	
	ProductSize findBySize(String size);

}
