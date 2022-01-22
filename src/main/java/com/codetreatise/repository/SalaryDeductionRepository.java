package com.codetreatise.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codetreatise.bean.Employee;
import com.codetreatise.bean.SalaryDeduction;
import com.codetreatise.bean.SalaryDeductionType;
import com.codetreatise.bean.Shop;

@Repository
public interface SalaryDeductionRepository extends JpaRepository<SalaryDeduction, Long> {

	List<SalaryDeduction> findByEmployeeAndLedgerMonth (Employee employee , String ledgerMonth);
	
	SalaryDeduction findByShop(Shop shop);
	
//	SalaryDeduction findByShopAndEmployeeAndLedgerMonth(Shop shop, Employee employee, String ledgerMonth);
	
	Optional<SalaryDeduction> findById(Long id);
	
	SalaryDeduction findByShopAndEmployee(Shop shop, Employee employee);
	
	List<SalaryDeduction> findBySalaryDeductionType (SalaryDeductionType salaryDeductionType);
	
	
}
