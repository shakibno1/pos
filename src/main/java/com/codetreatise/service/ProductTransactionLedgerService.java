package com.codetreatise.service;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.codetreatise.bean.*;
import org.springframework.transaction.annotation.Transactional;

import com.codetreatise.generic.GenericService;


public interface ProductTransactionLedgerService extends GenericService<ProductTransactionLedger> {
	
	Float getTotalReturnFromDateRangeAndCustomerPaymentStatus(Shop shop, Timestamp from, Timestamp to , String customerPaymentStatus);

	ProductTransactionLedger findByInvoiceAndProductStockAndProductTransactionType(Invoice invoice,ProductStock productStock,ProductTransactionType productTransactionType);

	Float getTotalStockInCountFromDateRangeAndShop(Shop shop,Timestamp from, Timestamp to);
	Float getTotalBuyingPriceFromDateRangeAndShop(Shop shop,Timestamp from,Timestamp to);
	Float getTotalSellingPriceFromDateRangeAndShop(Shop shop,Timestamp from,Timestamp to);
}
