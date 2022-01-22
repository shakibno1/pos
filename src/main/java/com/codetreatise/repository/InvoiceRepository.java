package com.codetreatise.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.codetreatise.bean.Customer;
import com.codetreatise.bean.Invoice;
import com.codetreatise.bean.Shop;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

	List<Invoice> findByCustomer(Customer customer);
	
	Invoice findByShop(Shop shop);
	
	Invoice findByCustomerAndShop(Customer customer, Shop shop);
	
//	@Query( value = "SELECT SUM(DISTINCT cb.amount) FROM invoice"
//			+ " inv JOIN customer c ON inv.customer_id=c.id JOIN customer_balance cb "
//			+ "ON cb.customer_id=c.id WHERE cb.created >= ?1 AND cb.created <= ?2 AND cb.shop_id= ?3 " , nativeQuery=true)
//    Float getTotalCustomerBalance(Timestamp from , Timestamp to ,Shop shop );
//	
//	@Query( value = "SELECT SUM(DISTINCT cb.amount) FROM invoice"
//			+ " inv JOIN customer c ON inv.customer_id=c.id JOIN customer_balance cb "
//			+ "ON cb.customer_id=c.id WHERE cb.created <= ?1 AND cb.shop_id= ?2 " , nativeQuery=true)
//    Float getTotalCustomerBalanceUptoFromTimeStamp(Timestamp from ,Shop shop );
	
}
