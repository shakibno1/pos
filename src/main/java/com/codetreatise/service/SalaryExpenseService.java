package com.codetreatise.service;

import java.sql.Timestamp;
import java.util.List;

import com.codetreatise.bean.Employee;
import com.codetreatise.bean.PayType;
import com.codetreatise.bean.SalaryExpense;
import com.codetreatise.bean.Shop;
import com.codetreatise.generic.GenericService;

public interface SalaryExpenseService extends GenericService<SalaryExpense> {

//	SalaryExpense findByEmployeeAndLedgerMonthAndPayType(Employee employee, String ledgerMonth, PayType payType);

	SalaryExpense findById(Long id);

	SalaryExpense findByEmployeeAndPayType(Employee employee, PayType payType);
	
	List<SalaryExpense> findByPayType(PayType payType);
	
	List<SalaryExpense> findByEmployeeAndLedgerMonth (Employee employee , String ledgerMonth);
	
	Float getTotalSalaryExpenseFromDateRange(Shop shop , Timestamp from , Timestamp to);
	
	Float getTotalSalaryExpenseBeforeFromDate(Shop shop , Timestamp from);
	
	List<SalaryExpense> findByShopAndCreatedBetween(Shop shop , Timestamp from , Timestamp to);

}
