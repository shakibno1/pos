//package com.codetreatise.service.impl;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.codetreatise.bean.ProductSold;
//import com.codetreatise.bean.ProductStock;
//import com.codetreatise.bean.Shop;
//import com.codetreatise.repository.ProductSoldRepository;
//import com.codetreatise.service.ProductSoldService;
//
//@Service
//public class ProductSoldServiceImpl implements ProductSoldService {
//
//	@Autowired
//	private ProductSoldRepository repository;
//
//	@Override
//	public ProductSold save(ProductSold entity) {
//		return repository.save(entity);
//	}
//
//	@Override
//	public ProductSold update(ProductSold entity) {
//		return repository.save(entity);
//	}
//
//	@Override
//	public void delete(ProductSold entity) {
//		repository.delete(entity);
//	}
//
//	@Override
//	public void delete(Long id) {
//		repository.delete(id);
//	}
//
//	@Override
//	public ProductSold find(Long id) {
//		return repository.findOne(id);
//	}
//
//	@Override
//	public List<ProductSold> findAll() {
//		return repository.findAll();
//	}
//
//	@Override
//	public void deleteInBatch(List<ProductSold> ProductSolds) {
//		repository.deleteInBatch(ProductSolds);
//	}
//
//	@Override
//	public ProductSold findById(Long id) {
//		return repository.findById(id);
//	}
//
//	@Override
//	public ProductSold findByProductStock(ProductStock productStock) {
//
//		return repository.findByProductStock(productStock);
//	}
//
//	@Override
//	public ProductSold findByProductStockAndShop(ProductStock productStock, Shop shop) {
//		// TODO Auto-generated method stub
//		return repository.findByProductStockAndShop(productStock, shop);
//	}
//
//}
