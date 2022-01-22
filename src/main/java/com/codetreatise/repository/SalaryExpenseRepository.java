package com.codetreatise.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.codetreatise.bean.Employee;
import com.codetreatise.bean.PayType;
import com.codetreatise.bean.SalaryExpense;
import com.codetreatise.bean.Shop;

@Repository
public interface SalaryExpenseRepository extends JpaRepository<SalaryExpense, Long> {

//	SalaryExpense findByEmployeeAndLedgerMonthAndPayType(Employee employee , String ledgerMonth , PayType payType);
	
	Optional<SalaryExpense> findById(Long id);
	
	SalaryExpense findByEmployeeAndPayType(Employee employee , PayType payType);
	
	List<SalaryExpense> findByPayType(PayType payType);
	
	List<SalaryExpense> findByEmployeeAndLedgerMonth (Employee employee , String ledgerMonth);
	
	@Query("SELECT SUM(s.amount) from SalaryExpense s where shop = ?1 AND s.created >= ?2 AND s.created <= ?3")
    Float getTotalSalaryExpenseFromDateRange(Shop shop , Timestamp from , Timestamp to);
	
	@Query("SELECT SUM(s.amount) from SalaryExpense s where shop = ?1 AND s.created <= ?2 ")
    Float getTotalSalaryExpenseBeforeFromDate(Shop shop , Timestamp from);
	
	List<SalaryExpense> findByShopAndCreatedBetween(Shop shop , Timestamp from , Timestamp to);

}
