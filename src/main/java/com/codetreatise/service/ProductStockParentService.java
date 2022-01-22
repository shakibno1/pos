package com.codetreatise.service;

import com.codetreatise.bean.ProductStockParent;
import com.codetreatise.generic.GenericService;

public interface ProductStockParentService extends GenericService<ProductStockParent> {

	ProductStockParent findById(Long id);

}
