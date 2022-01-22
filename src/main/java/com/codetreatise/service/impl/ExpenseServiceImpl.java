package com.codetreatise.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codetreatise.bean.Customer;
import com.codetreatise.bean.Expense;
import com.codetreatise.bean.ExpenseType;
import com.codetreatise.bean.Merchant;
import com.codetreatise.bean.Shop;
import com.codetreatise.repository.ExpenseRepository;
import com.codetreatise.service.ExpenseService;

@Service
public class ExpenseServiceImpl implements ExpenseService {

	@Autowired
	private ExpenseRepository repository;

	@Override
	public Expense save(Expense entity) {
		return repository.save(entity);
	}

	@Override
	public Expense update(Expense entity) {
		return repository.save(entity);
	}

	@Override
	public void delete(Expense entity) {
		repository.delete(entity);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	public List<Expense> findAll() {
		return repository.findAll();
	}

	@Override
	public void deleteInBatch(List<Expense> Expenses) {
		repository.deleteInBatch(Expenses);
	}

	@Override
	public Expense findByShop(Shop shop) {
		return repository.findByShop(shop);
	}

	@Override
	public Expense findByMerchant(Merchant merchant) {

		return repository.findByMerchant(merchant);
	}

	@Override
	public Expense findByMerchantAndShop(Merchant merchant, Shop shop) {
		return repository.findByMerchantAndShop(merchant, shop);
	}

	@Override
	public Expense findByShopAndExpenseType(Shop shop, ExpenseType expenseType) {
		return repository.findByShopAndExpenseType(shop, expenseType);
	}

	@Override
	public List<Expense> findByExpenseType(ExpenseType expenseType) {
		// TODO Auto-generated method stub
		return repository.findByExpenseType(expenseType);
	}

	@Override
	public Float getTotalExpenseFromDateRange(Shop shop, Timestamp from, Timestamp to) {
		// TODO Auto-generated method stub
		return repository.getTotalExpenseFromDateRange(shop, from, to);
	}

	@Override
	public Float getTotalExpenseBeforeFromDate(Shop shop, Timestamp from) {
		// TODO Auto-generated method stub
		return repository.getTotalExpenseBeforeFromDate(shop, from);
	}

	@Override
	public List<Expense> findByShopAndCreatedBetween(Shop shop, Timestamp from, Timestamp to) {
		// TODO Auto-generated method stub
		return repository.findByShopAndCreatedBetween(shop, from, to);
	}

	@Override
	public BigDecimal getTotalExpenseMerchantWiseOnlyToDate(Shop shop, Timestamp to, Merchant merchant,
			ExpenseType expenseType) {
		// TODO Auto-generated method stub
		return repository.getTotalExpenseMerchantWiseOnlyToDate(shop, to, merchant, expenseType);
	}

	@Override
	public BigDecimal getTotalExpenseMerchantWise(Shop shop, Timestamp from, Timestamp to, Merchant merchant,
			ExpenseType expenseType) {
		// TODO Auto-generated method stub
		return repository.getTotalExpenseMerchantWise(shop, from, to, merchant, expenseType);
	}

	@Override
	public Expense find(Long id) {
		Optional<Expense> expenseOptional = repository.findById(id);
		if (expenseOptional.isPresent()){
			Expense expense = expenseOptional.get();
			
			return expense;
		}
		else{
		   return null;
		}
	}

}
