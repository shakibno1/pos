package com.codetreatise.service;

import java.util.List;

import com.codetreatise.bean.Employee;
import com.codetreatise.bean.SalaryDeduction;
import com.codetreatise.bean.SalaryDeductionType;
import com.codetreatise.bean.Shop;
import com.codetreatise.generic.GenericService;

public interface SalaryDeductionService extends GenericService<SalaryDeduction> {

	SalaryDeduction findByShop(Shop shop);

//	SalaryDeduction findByShopAndEmployeeAndLedgerMonth(Shop shop, Employee employee, String ledgerMonth);

	SalaryDeduction findById(Long id);

	SalaryDeduction findByShopAndEmployee(Shop shop, Employee employee);
	
	List<SalaryDeduction> findByEmployeeAndLedgerMonth (Employee employee , String ledgerMonth);
	
	List<SalaryDeduction> findBySalaryDeductionType (SalaryDeductionType salaryDeductionType);

}
