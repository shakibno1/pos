package com.codetreatise.service;

import com.codetreatise.bean.SalaryDeductionType;
import com.codetreatise.generic.GenericService;

public interface SalaryDeductionTypeService extends GenericService<SalaryDeductionType> {

	SalaryDeductionType findByType(String type);

	SalaryDeductionType findById(Long id);

}
