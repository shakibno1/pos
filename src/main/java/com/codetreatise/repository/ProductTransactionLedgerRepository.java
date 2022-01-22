package com.codetreatise.repository;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.codetreatise.bean.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTransactionLedgerRepository extends JpaRepository<ProductTransactionLedger, Long> {
	
	@Query( value = "SELECT SUM(ps.price_selling) FROM product_transaction_ledger pt JOIN"
			+ " product_stock ps ON ps.id=pt.product_stock_id JOIN user_system us"
			+ " ON us.name =pt.created_by WHERE us.shop_id= ?1 AND"
			+ " pt.created >= ?2 AND pt.created <= ?3 AND"
			+ " product_transaction_type='CHANGE_RETURN' AND pt.customer_payment_type = ?4 " , nativeQuery=true)
    Float getTotalChangeAmountFromDateRange(Shop shop , Timestamp from , Timestamp to , String customerPaymentStatus);

	ProductTransactionLedger findByInvoiceAndProductStockAndProductTransactionType(Invoice invoice, ProductStock productStock, ProductTransactionType productTransactionType);

	@Query( value = "SELECT COUNT(id) FROM product_transaction_ledger "
			+ "WHERE shop_id= ?1 AND created >= ?2 AND created <= ?3 AND "
			+ "(product_transaction_type = 'STOCK_IN' OR product_transaction_type = 'STOCK_TRANSFER_IN')  " , nativeQuery=true)
	Float getTotalStockInCountFromDateRangeAndShop(Shop shop,Timestamp from, Timestamp to);

	@Query( value = " SELECT SUM(ps.price_buying) FROM product_transaction_ledger ptl INNER JOIN product_stock ps ON "
			+ " ptl.product_stock_id = ps.id "
			+ " WHERE ptl.shop_id=?1 AND ptl.created >= ?2 AND ptl.created <= ?3 AND "
			+ " ( ptl.product_transaction_type = 'STOCK_IN' OR ptl.product_transaction_type='STOCK_TRANSFER_IN')  " , nativeQuery=true)
	Float getTotalBuyingPriceFromDateRangeAndShop(Shop shop,Timestamp from,Timestamp to);

	@Query( value = " SELECT SUM(ps.price_selling) FROM product_transaction_ledger ptl INNER JOIN product_stock ps ON "
			+ " ptl.product_stock_id = ps.id "
			+ " WHERE ptl.shop_id=?1 AND ptl.created >= ?2 AND ptl.created <= ?3 AND "
			+ " ( ptl.product_transaction_type = 'STOCK_IN' OR ptl.product_transaction_type='STOCK_TRANSFER_IN')  " , nativeQuery=true)
	Float getTotalSellingPriceFromDateRangeAndShop(Shop shop,Timestamp from,Timestamp to);
}
