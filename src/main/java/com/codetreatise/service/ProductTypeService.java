package com.codetreatise.service;

import com.codetreatise.bean.ProductType;
import com.codetreatise.generic.GenericService;

public interface ProductTypeService extends GenericService<ProductType> {

	ProductType findByType(String type);

	ProductType findById(Long id);

}
