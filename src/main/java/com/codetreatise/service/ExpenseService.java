package com.codetreatise.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import com.codetreatise.bean.Expense;
import com.codetreatise.bean.ExpenseType;
import com.codetreatise.bean.Merchant;
import com.codetreatise.bean.Shop;
import com.codetreatise.generic.GenericService;

public interface ExpenseService extends GenericService<Expense> {

	Expense findByShop(Shop shop);

	Expense findByMerchant(Merchant merchant);

	Expense findByMerchantAndShop(Merchant merchant, Shop shop);

	Expense findByShopAndExpenseType(Shop shop, ExpenseType expenseType);

	List<Expense> findByExpenseType(ExpenseType expenseType);

	Float getTotalExpenseFromDateRange(Shop shop, Timestamp from, Timestamp to);

	Float getTotalExpenseBeforeFromDate(Shop shop, Timestamp from);
	
	List<Expense> findByShopAndCreatedBetween(Shop shop , Timestamp from , Timestamp to);
	
	BigDecimal getTotalExpenseMerchantWiseOnlyToDate( Shop shop  , Timestamp to , Merchant merchant , ExpenseType expenseType);

	BigDecimal getTotalExpenseMerchantWise( Shop shop  ,Timestamp from, Timestamp to , Merchant merchant , ExpenseType expenseType);

}
