package com.codetreatise.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codetreatise.bean.Customer;
import com.codetreatise.bean.Invoice;
import com.codetreatise.bean.Shop;
import com.codetreatise.repository.InvoiceRepository;
import com.codetreatise.service.InvoiceService;

@Service
public class InvoiceServiceImpl implements InvoiceService {

	@Autowired
	private InvoiceRepository repository;

	@Override
	@Transactional  (rollbackFor = Exception.class,  propagation = Propagation.REQUIRED)
	public Invoice save(Invoice entity) {
		return repository.save(entity);
	}

	@Override
	@Transactional  (rollbackFor = Exception.class ,  propagation = Propagation.REQUIRED)
	public Invoice update(Invoice entity) {
		return repository.save(entity);
	}

	@Override
	public void delete(Invoice entity) {
		repository.delete(entity);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	public List<Invoice> findAll() {
		return repository.findAll();
	}

	@Override
	public void deleteInBatch(List<Invoice> Invoices) {
		repository.deleteInBatch(Invoices);
	}

	@Override
	public List<Invoice> findByCustomer(Customer customer) {
		return repository.findByCustomer(customer);
	}

	@Override
	public Invoice findByShop(Shop shop) {
		return repository.findByShop(shop);
	}

	@Override
	public Invoice findByCustomerAndShop(Customer customer, Shop shop) {
		return repository.findByCustomerAndShop(customer, shop);
	}

	@Override
	public Invoice find(Long id) {
		Optional<Invoice> invoiceOptional = repository.findById(id);
		if (invoiceOptional.isPresent()){
			Invoice invoice = invoiceOptional.get();
			
			return invoice;
		}
		else{
		   return null;
		}
	}

//	@Override
//	public Float getTotalCustomerBalance(Timestamp from, Timestamp to, Shop shop) {
//		// TODO Auto-generated method stub
//		return repository.getTotalCustomerBalance(from, to, shop);
//	}
//
//	@Override
//	public Float getTotalCustomerBalanceUptoFromTimeStamp(Timestamp from, Shop shop) {
//		// TODO Auto-generated method stub
//		return repository.getTotalCustomerBalanceUptoFromTimeStamp(from, shop);
//	}

}
