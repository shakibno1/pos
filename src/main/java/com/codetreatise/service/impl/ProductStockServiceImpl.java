package com.codetreatise.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codetreatise.bean.Customer;
import com.codetreatise.bean.CustomerPaymentStatus;
import com.codetreatise.bean.Invoice;
import com.codetreatise.bean.Merchant;
import com.codetreatise.bean.ProductStock;
import com.codetreatise.bean.ProductStockParent;
import com.codetreatise.bean.ProductStockStatus;
import com.codetreatise.bean.ProductType;
import com.codetreatise.bean.Shop;
import com.codetreatise.repository.ProductStockRepository;
import com.codetreatise.service.ProductStockService;

@Service
public class ProductStockServiceImpl implements ProductStockService {
	
	@Autowired
	private ProductStockRepository repository;
	
	@Transactional  (rollbackFor = Exception.class ,  propagation = Propagation.REQUIRED)
	@Override
	public ProductStock save(ProductStock entity) {
		return repository.save(entity);
	}

	@Transactional  (rollbackFor = Exception.class ,  propagation = Propagation.REQUIRED)
	@Override
	public ProductStock update(ProductStock entity) {
		return repository.save(entity);
	}

	@Override
	public void delete(ProductStock entity) {
		repository.delete(entity);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	public List<ProductStock> findAll() {
		return repository.findAll();
	}

	@Override
	public void deleteInBatch(List<ProductStock> ProductStocks) {
		repository.deleteInBatch(ProductStocks);
	}

	@Override
	public List<ProductStock> findByProductStockParentAndProductStockStatus(ProductStockParent productStockParent , ProductStockStatus productStockStatus) {
		return repository.findByProductStockParentAndProductStockStatus(productStockParent, productStockStatus);
	}

	@Override
	public ProductStock findByIdAndProductStockStatus(Long id, ProductStockStatus productStockStatus) {
		return repository.findByIdAndProductStockStatus(id, productStockStatus);
	}

	@Override
	public List<ProductStock> findByProductType(ProductType productType) {
		return repository.findByProductType(productType);
	}

	@Override
	public List<ProductStock> findByMerchant(Merchant merchant) {
		return repository.findByMerchant(merchant);
	}

	@Override
	public Float getSumOfPriceSelling() {
		return repository.getSumPriceSelling();
	}

	@Override
	public Float getTotalSellBeforeFromDate(ProductStockStatus productStockStatus, Shop shop, Timestamp from) {
		// TODO Auto-generated method stub
		return repository.getTotalSellBeforeFromDate(productStockStatus, shop, from);
	}

	@Override
	public List<ProductStock> findByShopAndProductStockStatusAndUpdatedBetween(Shop shop,
			ProductStockStatus productStockStatus, Timestamp from, Timestamp to) {
		// TODO Auto-generated method stub
		return repository.findByShopAndProductStockStatusAndUpdatedBetweenOrderByUpdatedAsc(shop, productStockStatus, from, to);
	}

	@Override
	public Float getTotalSell(ProductStockStatus productStockStatus, Shop shop,
			CustomerPaymentStatus customerPaymentStatus, Timestamp from, Timestamp to) {
		// TODO Auto-generated method stub
		return repository.getTotalSell(productStockStatus, shop, customerPaymentStatus, from, to);
	}

	@Override
	public ProductStock findByIdAndShop(Long id, Shop shop) {
		// TODO Auto-generated method stub
		return repository.findByIdAndShop(id, shop);
	}

	@Override
	public Float getTotalDiscount(ProductStockStatus productStockStatus, Shop shop, Timestamp from, Timestamp to) {
		// TODO Auto-generated method stub
		return repository.getTotalDiscount(productStockStatus, shop, from, to);
	}

	@Override
	public BigDecimal getAvailableProductCount(ProductStockStatus productStockStatus , Shop shop  , Timestamp from , Timestamp to) {
		// TODO Auto-generated method stub
		return repository.getAvailableProductCount(productStockStatus , shop  , from , to);
	}

	@Override
	public BigDecimal getAvailableProductInBuyingPrice(ProductStockStatus productStockStatus , Shop shop  , Timestamp from , Timestamp to) {
		// TODO Auto-generated method stub
		return repository.getAvailableProductInBuyingPrice(productStockStatus , shop  , from , to);
	}

	@Override
	public BigDecimal getAvailableProductInSellingPrice(ProductStockStatus productStockStatus , Shop shop  , Timestamp from , Timestamp to) {
		// TODO Auto-generated method stub
		return repository.getAvailableProductInSellingPrice(productStockStatus , shop  , from , to);
	}

	@Override
	public BigDecimal getAvailableProductCountOnlyToDate(ProductStockStatus productStockStatus, Shop shop,
			Timestamp to) {
		// TODO Auto-generated method stub
		return repository.getAvailableProductCountOnlyToDate(productStockStatus, shop, to);
	}

	@Override
	public BigDecimal getAvailableProductInBuyingPriceOnlyToDate(ProductStockStatus productStockStatus, Shop shop, Timestamp to) {
		// TODO Auto-generated method stub
		return repository.getAvailableProductInBuyingPriceOnlyToDate(productStockStatus, shop, to);
	}

	@Override
	public BigDecimal getAvailableProductInSellingPriceOnlyToDate(ProductStockStatus productStockStatus, Shop shop, Timestamp to) {
		// TODO Auto-generated method stub
		return repository.getAvailableProductInSellingPriceOnlyToDate(productStockStatus, shop, to);
	}

	@Override
	public List<ProductStock> findByShopAndMerchantAndCreatedBetween(Shop shop, Merchant merchant, Timestamp from,
			Timestamp to) {
		// TODO Auto-generated method stub
		return repository.findByShopAndMerchantAndCreatedBetween(shop, merchant, from, to);
	}

	@Override
	public List<ProductStock> findByShopAndMerchantAndProductStockStatusAndCreatedBetween(Shop shop, Merchant merchant,
			ProductStockStatus productStockStatus, Timestamp from, Timestamp to) {
		// TODO Auto-generated method stub
		return repository.findByShopAndMerchantAndProductStockStatusAndCreatedBetween(shop, merchant, productStockStatus, from, to);
	}

	@Override
	public BigDecimal getAvailableProductCountOnlyToDateTypeWise(ProductStockStatus productStockStatus, Shop shop,
			Timestamp to, ProductType productType) {
		// TODO Auto-generated method stub
		return repository.getAvailableProductCountOnlyToDateTypeWise(productStockStatus, shop, to, productType);
	}

	@Override
	public BigDecimal getAvailableProductInBuyingPriceOnlyToDateTypeWise(ProductStockStatus productStockStatus,
			Shop shop, Timestamp to, ProductType productType) {
		// TODO Auto-generated method stub
		return repository.getAvailableProductInBuyingPriceOnlyToDateTypeWise(productStockStatus, shop, to, productType);
	}

	@Override
	public BigDecimal getAvailableProductInSellingPriceOnlyToDateTypeWise(ProductStockStatus productStockStatus,
			Shop shop, Timestamp to, ProductType productType) {
		// TODO Auto-generated method stub
		return repository.getAvailableProductInSellingPriceOnlyToDateTypeWise(productStockStatus, shop, to, productType);
	}

	@Override
	public BigDecimal getAvailableProductCountTypeWise(ProductStockStatus productStockStatus, Shop shop, Timestamp from,
			Timestamp to, ProductType productType) {
		// TODO Auto-generated method stub
		return repository.getAvailableProductCountTypeWise(productStockStatus, shop, from, to, productType);
	}

	@Override
	public BigDecimal getAvailableProductInBuyingPriceTypeWise(ProductStockStatus productStockStatus, Shop shop,
			Timestamp from, Timestamp to, ProductType productType) {
		// TODO Auto-generated method stub
		return repository.getAvailableProductInBuyingPriceTypeWise(productStockStatus, shop, from, to, productType);
	}

	@Override
	public BigDecimal getAvailableProductInSellingPriceTypeWise(ProductStockStatus productStockStatus, Shop shop,
			Timestamp from, Timestamp to, ProductType productType) {
		// TODO Auto-generated method stub
		return repository.getAvailableProductInSellingPriceTypeWise(productStockStatus, shop, from, to, productType);
	}

	@Override
	public BigDecimal getTotalProductCountMerchantWise(Shop shop, Timestamp from, Timestamp to , Merchant merchant) {
		// TODO Auto-generated method stub
		return repository.getTotalProductCountMerchantWise(shop, from, to , merchant);
	}

	@Override
	public BigDecimal getTotalAvailableProductCountMerchantWise(Shop shop, Timestamp from, Timestamp to,
			ProductStockStatus productStockStatus , Merchant merchant) {
		// TODO Auto-generated method stub
		return repository.getTotalAvailableProductCountMerchantWise(shop, from, to, productStockStatus , merchant);
	}

	@Override
	public BigDecimal getTotalProductCountMerchantWiseOnlyToDate(Shop shop, Timestamp to , Merchant merchant) {
		// TODO Auto-generated method stub
		return repository.getTotalProductCountMerchantWiseOnlyToDate(shop, to , merchant);
	}

	@Override
	public BigDecimal getTotalAvailableProductCountMerchantWiseToDate(Shop shop, Timestamp to,
			ProductStockStatus productStockStatus , Merchant merchant) {
		// TODO Auto-generated method stub
		return repository.getTotalAvailableProductCountMerchantWiseToDate(shop, to, productStockStatus, merchant);
	}

	@Override
	public BigDecimal getAvailableProductInSellingPriceMerchantWiseOnlyToDate(ProductStockStatus productStockStatus,
			Shop shop, Timestamp to, Merchant merchant) {
		// TODO Auto-generated method stub
		return repository.getAvailableProductInSellingPriceMerchantWiseOnlyToDate(productStockStatus, shop, to, merchant);
	}

	@Override
	public BigDecimal getTotalProductInSellingPriceMerchantWiseOnlyToDate(Shop shop, Timestamp to, Merchant merchant) {
		// TODO Auto-generated method stub
		return repository.getTotalProductInSellingPriceMerchantWiseOnlyToDate(shop, to, merchant);
	}

	@Override
	public BigDecimal getAvailableProductInSellingPriceMerchantWise(ProductStockStatus productStockStatus, Shop shop,
			Timestamp from, Timestamp to, Merchant merchant) {
		// TODO Auto-generated method stub
		return repository.getAvailableProductInSellingPriceMerchantWise(productStockStatus, shop, from, to, merchant);
	}

	@Override
	public BigDecimal getTotalProductInSellingPriceMerchantWise(Shop shop, Timestamp from, Timestamp to,
			Merchant merchant) {
		// TODO Auto-generated method stub
		return repository.getTotalProductInSellingPriceMerchantWise(shop, from, to, merchant);
	}

	@Override
	public BigDecimal getAvailableProductInBuyingPriceMerchantWiseOnlyToDate(ProductStockStatus productStockStatus,
			Shop shop, Timestamp to, Merchant merchant) {
		// TODO Auto-generated method stub
		return repository.getAvailableProductInBuyingPriceMerchantWiseOnlyToDate(productStockStatus, shop, to, merchant);
	}

	@Override
	public BigDecimal getTotalProductInBuyingPriceMerchantWiseOnlyToDate(Shop shop, Timestamp to, Merchant merchant) {
		// TODO Auto-generated method stub
		return repository.getTotalProductInBuyingPriceMerchantWiseOnlyToDate(shop, to, merchant);
	}

	@Override
	public BigDecimal getAvailableProductInBuyingPriceMerchantWise(ProductStockStatus productStockStatus, Shop shop,
			Timestamp from, Timestamp to, Merchant merchant) {
		// TODO Auto-generated method stub
		return repository.getAvailableProductInBuyingPriceMerchantWise(productStockStatus, shop, from, to, merchant);
	}

	@Override
	public BigDecimal getTotalProductInBuyingPriceMerchantWise(Shop shop, Timestamp from, Timestamp to,
			Merchant merchant) {
		// TODO Auto-generated method stub
		return repository.getTotalProductInBuyingPriceMerchantWise(shop, from, to, merchant);
	}

	@Override
	public List<ProductStock> findByProductStockParentAndProductStockStatusAndShop(
			ProductStockParent productStockParent, ProductStockStatus productStockStatus, Shop shop) {
		// TODO Auto-generated method stub
		return repository.findByProductStockParentAndProductStockStatusAndShop(productStockParent, productStockStatus, shop);
	}

	@Override
	public ProductStock findByIdAndProductStockStatusAndShop(Long id, ProductStockStatus productStockStatus,
			Shop shop) {
		// TODO Auto-generated method stub
		return repository.findByIdAndProductStockStatusAndShop(id, productStockStatus, shop);
	}

	@Override
	public ProductStock find(Long id) {
		Optional<ProductStock> productStockOptional = repository.findById(id);
		if (productStockOptional.isPresent()){
			ProductStock productStock = productStockOptional.get();
			
			return productStock;
		}
		else{
		   return null;
		}
	}

	@Override
	public ProductStock findById(Long id) {
		Optional<ProductStock> productStockOptional = repository.findById(id);
		if (productStockOptional.isPresent()){
			ProductStock productStock = productStockOptional.get();
			
			return productStock;
		}
		else{
		   return null;
		}
	}

	@Override
	public List<ProductStock> findByInvoice(Invoice invoice) {
		// TODO Auto-generated method stub
		return repository.findByInvoice(invoice);
	}
	
}
