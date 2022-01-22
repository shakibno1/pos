//package com.codetreatise.repository;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import com.codetreatise.bean.ProductSold;
//import com.codetreatise.bean.ProductStock;
//import com.codetreatise.bean.Shop;
//
//@Repository
//public interface ProductSoldRepository extends JpaRepository<ProductSold, Long> {
//
//	ProductSold findByProductStock(ProductStock productStock);
//	
//	ProductSold findById(Long id);
//	
//	ProductSold findByProductStockAndShop(ProductStock productStock, Shop shop);
//}
