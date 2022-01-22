package com.codetreatise.service;

import com.codetreatise.bean.PayType;
import com.codetreatise.generic.GenericService;

public interface PayTypeService extends GenericService<PayType> {

	PayType findByType(String type);

	PayType findById(Long id);

}
