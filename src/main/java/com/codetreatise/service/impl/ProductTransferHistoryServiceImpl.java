package com.codetreatise.service.impl;

import java.util.List;

import com.codetreatise.bean.ProductStock;
import com.codetreatise.bean.ProductTransferStatus;
import com.codetreatise.bean.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codetreatise.bean.ProductTransferHistory;
import com.codetreatise.repository.ProductTransferHistoryRepository;
import com.codetreatise.service.ProductTransferHistoryService;

@Service
public class ProductTransferHistoryServiceImpl implements ProductTransferHistoryService {

	@Autowired
	private ProductTransferHistoryRepository repository;

	@Transactional  (rollbackFor = Exception.class ,  propagation = Propagation.REQUIRED)
	@Override
	public ProductTransferHistory save(ProductTransferHistory entity) {
		return repository.save(entity);
	}
	
	@Transactional (rollbackFor = Exception.class ,  propagation = Propagation.REQUIRED)
	@Override
	public ProductTransferHistory update(ProductTransferHistory entity) {
		return repository.save(entity);
	}

	@Override
	public void delete(ProductTransferHistory entity) {
		repository.delete(entity);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	public List<ProductTransferHistory> findAll() {
		return repository.findAll();
	}

	@Override
	public void deleteInBatch(List<ProductTransferHistory> entities) {
		repository.deleteInBatch(entities);
		
	}

	@Override
	public ProductTransferHistory find(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getMaxTransferBatch() {
		return repository.getMaxTransferBatch();
	}

	@Override
	public List<String> getAllTransferBatches() {
		return repository.getAllTransferBatches();
	}

	@Override
	public List<ProductTransferHistory> findByTransferBatch(Integer transferBatch) {
		return repository.findByTransferBatch(transferBatch);
	}

	@Override
	public List<ProductTransferHistory> findByTransferBatchAndProductTransferStatusAndToShop(Integer transferBatch, ProductTransferStatus productTransferStatus, Shop toShop) {
		return repository.findByTransferBatchAndProductTransferStatusAndToShop(transferBatch,productTransferStatus,toShop);
	}

	@Override
	public ProductTransferHistory findByProductStockAndToShopAndProductTransferStatus(ProductStock productStock, Shop toShop, ProductTransferStatus productTransferStatus) {
		return repository.findByProductStockAndToShopAndProductTransferStatus(productStock,toShop,productTransferStatus);
	}
}
