package com.codetreatise.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import com.codetreatise.bean.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codetreatise.repository.CustomerRepository;
import com.codetreatise.repository.ProductTransactionLedgerRepository;
import com.codetreatise.service.CustomerService;
import com.codetreatise.service.ProductTransactionLedgerService;

@Service
public class ProductTransactionLedgerServiceImpl implements ProductTransactionLedgerService {

	@Autowired
	private ProductTransactionLedgerRepository repository;

	@Transactional  (rollbackFor = Exception.class ,  propagation = Propagation.REQUIRED)
	@Override
	public ProductTransactionLedger save(ProductTransactionLedger entity) {
		return repository.save(entity);
	}
	
	@Transactional (rollbackFor = Exception.class ,  propagation = Propagation.REQUIRED)
	@Override
	public ProductTransactionLedger update(ProductTransactionLedger entity) {
		return repository.save(entity);
	}

	@Override
	public void delete(ProductTransactionLedger entity) {
		repository.delete(entity);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	public List<ProductTransactionLedger> findAll() {
		return repository.findAll();
	}

	@Override
	public void deleteInBatch(List<ProductTransactionLedger> entities) {
		repository.deleteInBatch(entities);
		
	}

	@Override
	public ProductTransactionLedger find(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Float getTotalReturnFromDateRangeAndCustomerPaymentStatus(Shop shop, Timestamp from, Timestamp to,
			String customerPaymentStatus) {
		return repository.getTotalChangeAmountFromDateRange(shop, from, to, customerPaymentStatus);
	}

	@Override
	public ProductTransactionLedger findByInvoiceAndProductStockAndProductTransactionType(Invoice invoice, ProductStock productStock, ProductTransactionType productTransactionType) {
		return repository.findByInvoiceAndProductStockAndProductTransactionType(invoice,productStock,productTransactionType);
	}

	@Override
	public Float getTotalStockInCountFromDateRangeAndShop(Shop shop,Timestamp from, Timestamp to) {
		return repository.getTotalStockInCountFromDateRangeAndShop(shop,from,to);
	}

	@Override
	public Float getTotalBuyingPriceFromDateRangeAndShop(Shop shop,Timestamp from, Timestamp to) {
		return repository.getTotalBuyingPriceFromDateRangeAndShop(shop,from,to);
	}

	@Override
	public Float getTotalSellingPriceFromDateRangeAndShop(Shop shop,Timestamp from, Timestamp to) {
		return repository.getTotalSellingPriceFromDateRangeAndShop(shop,from,to);
	}

}
