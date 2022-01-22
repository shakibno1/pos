package com.codetreatise.repository;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.codetreatise.bean.Customer;
import com.codetreatise.bean.CustomerBalance;
import com.codetreatise.bean.ProductStockStatus;
import com.codetreatise.bean.Shop;

@Repository
public interface CustomerBalanceRepository extends JpaRepository<CustomerBalance, Long> {

	CustomerBalance findByCustomer(Customer customer);
	
	CustomerBalance findByShop(Shop shop);
	
	CustomerBalance findByCustomerAndShop(Customer customer, Shop shop);
	
	CustomerBalance findByCustomerAndShopAndAmountNot(Customer customer, Shop shop , Float amount);
	
	@Query("SELECT SUM(cb.amount) from CustomerBalance cb where cb.shop = ?1 AND created <= ?2")
	Float getTotalCustomerBalanceUptoFromTimeStamp(Shop shop  , Timestamp from);
	
	@Query("SELECT SUM(cb.amount) from CustomerBalance cb where cb.shop = ?1 AND created >= ?2 AND created <= ?3")
	Float getTotalCustomerBalance(Shop shop  , Timestamp from ,  Timestamp to);
}
