package com.codetreatise.repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.codetreatise.bean.Expense;
import com.codetreatise.bean.ExpenseType;
import com.codetreatise.bean.Merchant;
import com.codetreatise.bean.SalaryExpense;
import com.codetreatise.bean.Shop;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

	Expense findByShop(Shop shop);
	
	Expense findByMerchant(Merchant merchant);
	
	Expense findByMerchantAndShop(Merchant merchant, Shop shop);
	
	Expense findByShopAndExpenseType(Shop shop , ExpenseType expenseType);
	
	List<Expense> findByExpenseType(ExpenseType expenseType);
	
	@Query("SELECT SUM(s.amount) from Expense s where shop = ?1 AND s.expenseDate >= ?2 AND s.expenseDate <= ?3")
    Float getTotalExpenseFromDateRange(Shop shop , Timestamp from , Timestamp to);
	
	@Query("SELECT SUM(s.amount) from Expense s where shop = ?1 AND s.expenseDate <= ?2 ")
    Float getTotalExpenseBeforeFromDate(Shop shop , Timestamp from);
	
	List<Expense> findByShopAndCreatedBetween(Shop shop , Timestamp from , Timestamp to);
	
	@Query("SELECT SUM(s.amount) from Expense s where shop = ?1 AND expenseDate >= ?2 AND expenseDate <= ?3 AND merchant = ?4 AND expenseType = ?5")
	BigDecimal getTotalExpenseMerchantWise( Shop shop  ,Timestamp from, Timestamp to , Merchant merchant , ExpenseType expenseType);
	
	@Query("SELECT SUM(s.amount) from Expense s where shop = ?1 AND expenseDate <= ?2 AND merchant = ?3 AND expenseType = ?4")
	BigDecimal getTotalExpenseMerchantWiseOnlyToDate( Shop shop  , Timestamp to , Merchant merchant , ExpenseType expenseType);
}
