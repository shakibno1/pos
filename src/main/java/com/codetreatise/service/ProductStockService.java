package com.codetreatise.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.codetreatise.bean.CustomerPaymentStatus;
import com.codetreatise.bean.Invoice;
import com.codetreatise.bean.Merchant;
import com.codetreatise.bean.ProductStock;
import com.codetreatise.bean.ProductStockParent;
import com.codetreatise.bean.ProductStockStatus;
import com.codetreatise.bean.ProductType;
import com.codetreatise.bean.Shop;
import com.codetreatise.generic.GenericService;

public interface ProductStockService extends GenericService<ProductStock> {

	ProductStock findById(Long id);
	
	ProductStock findByIdAndShop(Long id , Shop shop);
	
	List<ProductStock> findByInvoice(Invoice invoice);
	
	ProductStock findByIdAndProductStockStatus(Long id , ProductStockStatus productStockStatus);
	
	ProductStock findByIdAndProductStockStatusAndShop(Long id , ProductStockStatus productStockStatus,Shop shop);
	
	List<ProductStock> findByProductStockParentAndProductStockStatus(ProductStockParent productStockParent , ProductStockStatus productStockStatus);
	
	List<ProductStock> findByProductStockParentAndProductStockStatusAndShop(ProductStockParent productStockParent , ProductStockStatus productStockStatus , Shop shop);
	
	List<ProductStock> findByProductType(ProductType productType);
	
	List<ProductStock> findByMerchant(Merchant merchant);
	
	Float getSumOfPriceSelling();
	
	BigDecimal getAvailableProductCountOnlyToDate(ProductStockStatus productStockStatus , Shop shop , Timestamp to);
	
	BigDecimal getAvailableProductCountOnlyToDateTypeWise(ProductStockStatus productStockStatus , Shop shop , Timestamp to , ProductType productType);
	
	BigDecimal getAvailableProductInBuyingPriceOnlyToDate(ProductStockStatus productStockStatus , Shop shop , Timestamp to);
	
	BigDecimal getAvailableProductInBuyingPriceOnlyToDateTypeWise(ProductStockStatus productStockStatus , Shop shop  , Timestamp to , ProductType productType);
	
	BigDecimal getAvailableProductInSellingPriceOnlyToDate(ProductStockStatus productStockStatus , Shop shop , Timestamp to);
	
	BigDecimal getAvailableProductInSellingPriceOnlyToDateTypeWise(ProductStockStatus productStockStatus , Shop shop  , Timestamp to , ProductType productType);
	
	BigDecimal getAvailableProductCount(ProductStockStatus productStockStatus , Shop shop  , Timestamp from , Timestamp to);
	
	BigDecimal getAvailableProductCountTypeWise(ProductStockStatus productStockStatus , Shop shop  , Timestamp from , Timestamp to , ProductType productType);
	
	BigDecimal getAvailableProductInBuyingPrice(ProductStockStatus productStockStatus , Shop shop  , Timestamp from , Timestamp to);
	
	BigDecimal getAvailableProductInBuyingPriceTypeWise(ProductStockStatus productStockStatus , Shop shop  , Timestamp from , Timestamp to , ProductType productType);
	
	BigDecimal getAvailableProductInSellingPrice(ProductStockStatus productStockStatus , Shop shop  , Timestamp from , Timestamp to);
	
	BigDecimal getAvailableProductInSellingPriceTypeWise(ProductStockStatus productStockStatus , Shop shop  , Timestamp from , Timestamp to , ProductType productType);
	
	Float getTotalSell(ProductStockStatus productStockStatus , Shop shop , CustomerPaymentStatus customerPaymentStatus , Timestamp from , Timestamp to);
	
	Float getTotalDiscount(ProductStockStatus productStockStatus , Shop shop , Timestamp from , Timestamp to);
	
	Float getTotalSellBeforeFromDate(ProductStockStatus productStockStatus , Shop shop , Timestamp from);
	
	List<ProductStock> findByShopAndProductStockStatusAndUpdatedBetween(Shop shop ,ProductStockStatus productStockStatus , Timestamp from , Timestamp to);

	List<ProductStock> findByShopAndMerchantAndCreatedBetween(Shop shop ,Merchant merchant , Timestamp from , Timestamp to);
	
	List<ProductStock> findByShopAndMerchantAndProductStockStatusAndCreatedBetween(Shop shop ,Merchant merchant ,ProductStockStatus productStockStatus, Timestamp from , Timestamp to);
	
	BigDecimal getTotalProductCountMerchantWise( Shop shop  , Timestamp from , Timestamp to , Merchant merchant);
	
	BigDecimal getTotalAvailableProductCountMerchantWise( Shop shop  , Timestamp from , Timestamp to ,ProductStockStatus productStockStatus , Merchant merchant);
	
	BigDecimal getTotalProductCountMerchantWiseOnlyToDate( Shop shop  , Timestamp to , Merchant merchant);
	
	BigDecimal getTotalAvailableProductCountMerchantWiseToDate( Shop shop  , Timestamp to ,ProductStockStatus productStockStatus , Merchant merchant);
	
	BigDecimal getAvailableProductInSellingPriceMerchantWiseOnlyToDate(ProductStockStatus productStockStatus , Shop shop  , Timestamp to , Merchant merchant);
	
	BigDecimal getTotalProductInSellingPriceMerchantWiseOnlyToDate( Shop shop  , Timestamp to , Merchant merchant);
	
	BigDecimal getAvailableProductInSellingPriceMerchantWise(ProductStockStatus productStockStatus , Shop shop  ,Timestamp from , Timestamp to , Merchant merchant);
	
	BigDecimal getTotalProductInSellingPriceMerchantWise( Shop shop  ,Timestamp from, Timestamp to , Merchant merchant);
	
	BigDecimal getAvailableProductInBuyingPriceMerchantWiseOnlyToDate(ProductStockStatus productStockStatus , Shop shop  , Timestamp to , Merchant merchant);
	
	BigDecimal getTotalProductInBuyingPriceMerchantWiseOnlyToDate( Shop shop  , Timestamp to , Merchant merchant);
	
	BigDecimal getAvailableProductInBuyingPriceMerchantWise(ProductStockStatus productStockStatus , Shop shop  ,Timestamp from , Timestamp to , Merchant merchant);
	
	BigDecimal getTotalProductInBuyingPriceMerchantWise( Shop shop  ,Timestamp from, Timestamp to , Merchant merchant);

}
