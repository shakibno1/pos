package com.codetreatise.service;

import com.codetreatise.bean.ExpenseType;
import com.codetreatise.generic.GenericService;

public interface ExpenseTypeService extends GenericService<ExpenseType> {

	ExpenseType findByType(String type);

	ExpenseType findById(Long id);

}
