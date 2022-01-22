package com.codetreatise.repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.codetreatise.bean.CustomerPaymentStatus;
import com.codetreatise.bean.Invoice;
import com.codetreatise.bean.Merchant;
import com.codetreatise.bean.ProductStock;
import com.codetreatise.bean.ProductStockParent;
import com.codetreatise.bean.ProductStockStatus;
import com.codetreatise.bean.ProductType;
import com.codetreatise.bean.Shop;

@Repository
public interface ProductStockRepository extends JpaRepository<ProductStock, Long> {


	Optional<ProductStock> findById(Long id);
	
	ProductStock findByIdAndShop(Long id , Shop shop);
	
	List<ProductStock> findByInvoice(Invoice invoice);
	
	ProductStock findByIdAndProductStockStatus(Long id , ProductStockStatus productStockStatus);
	
	ProductStock findByIdAndProductStockStatusAndShop(Long id , ProductStockStatus productStockStatus,Shop shop);
	
	List<ProductStock> findByProductType(ProductType productType);
	
	List<ProductStock> findByProductStockParentAndProductStockStatus(ProductStockParent productStockParent , ProductStockStatus productStockStatus);
	
	List<ProductStock> findByProductStockParentAndProductStockStatusAndShop(ProductStockParent productStockParent , ProductStockStatus productStockStatus , Shop shop);
	
	List<ProductStock> findByMerchant(Merchant merchant);
	
	@Query("SELECT SUM(s.priceSelling) from ProductStock s")
    Float getSumPriceSelling();
	
	@Query("SELECT COUNT(s.id) from ProductStock s where s.productStockStatus = ?1 AND shop = ?2 AND updated <= ?3")
	BigDecimal getAvailableProductCountOnlyToDate(ProductStockStatus productStockStatus , Shop shop , Timestamp to);
	
	@Query("SELECT COUNT(s.id) from ProductStock s where s.productStockStatus = ?1 AND shop = ?2 AND updated <= ?3 AND productType = ?4")
	BigDecimal getAvailableProductCountOnlyToDateTypeWise(ProductStockStatus productStockStatus , Shop shop , Timestamp to , ProductType productType);
	
	@Query("SELECT SUM(s.priceBuying) from ProductStock s where s.productStockStatus = ?1 AND shop = ?2 AND updated <= ?3")
	BigDecimal getAvailableProductInBuyingPriceOnlyToDate(ProductStockStatus productStockStatus , Shop shop  , Timestamp to);
	
	@Query("SELECT SUM(s.priceBuying) from ProductStock s where s.productStockStatus = ?1 AND shop = ?2 AND updated <= ?3 AND productType = ?4")
	BigDecimal getAvailableProductInBuyingPriceOnlyToDateTypeWise(ProductStockStatus productStockStatus , Shop shop  , Timestamp to , ProductType productType);
	
	@Query("SELECT SUM(s.priceSelling) from ProductStock s where s.productStockStatus = ?1 AND shop = ?2 AND updated <= ?3")
	BigDecimal getAvailableProductInSellingPriceOnlyToDate(ProductStockStatus productStockStatus , Shop shop  , Timestamp to);
	
	@Query("SELECT SUM(s.priceSelling) from ProductStock s where s.productStockStatus = ?1 AND shop = ?2 AND updated <= ?3 AND productType = ?4")
	BigDecimal getAvailableProductInSellingPriceOnlyToDateTypeWise(ProductStockStatus productStockStatus , Shop shop  , Timestamp to , ProductType productType);
	
	@Query("SELECT COUNT(s.id) from ProductStock s where s.productStockStatus = ?1 AND shop = ?2  AND updated >= ?3 AND updated <= ?4")
	BigDecimal getAvailableProductCount(ProductStockStatus productStockStatus , Shop shop  , Timestamp from , Timestamp to);
	
	@Query("SELECT COUNT(s.id) from ProductStock s where s.productStockStatus = ?1 AND shop = ?2  AND updated >= ?3 AND updated <= ?4 AND productType = ?5 ")
	BigDecimal getAvailableProductCountTypeWise(ProductStockStatus productStockStatus , Shop shop  , Timestamp from , Timestamp to , ProductType productType);
	
	@Query("SELECT SUM(s.priceBuying) from ProductStock s where s.productStockStatus = ?1 AND shop = ?2  AND updated >= ?3 AND updated <= ?4")
	BigDecimal getAvailableProductInBuyingPrice(ProductStockStatus productStockStatus , Shop shop  , Timestamp from , Timestamp to);
	
	@Query("SELECT SUM(s.priceBuying) from ProductStock s where s.productStockStatus = ?1 AND shop = ?2  AND updated >= ?3 AND updated <= ?4 AND productType = ?5")
	BigDecimal getAvailableProductInBuyingPriceTypeWise(ProductStockStatus productStockStatus , Shop shop  , Timestamp from , Timestamp to , ProductType productType);
	
	@Query("SELECT SUM(s.priceSelling) from ProductStock s where s.productStockStatus = ?1 AND shop = ?2  AND updated >= ?3 AND updated <= ?4")
	BigDecimal getAvailableProductInSellingPrice(ProductStockStatus productStockStatus , Shop shop  , Timestamp from , Timestamp to);
	
	@Query("SELECT SUM(s.priceSelling) from ProductStock s where s.productStockStatus = ?1 AND shop = ?2  AND updated >= ?3 AND updated <= ?4 AND productType = ?5")
	BigDecimal getAvailableProductInSellingPriceTypeWise(ProductStockStatus productStockStatus , Shop shop  , Timestamp from , Timestamp to , ProductType productType);

	@Query("SELECT SUM(s.priceSelling - IFNULL(s.discount, 0)) from ProductStock s where s.productStockStatus = ?1 AND shop = ?2 AND s.customerPaymentType = ?3 AND updated >= ?4 AND updated <= ?5")
    Float getTotalSell(ProductStockStatus productStockStatus , Shop shop , CustomerPaymentStatus customerPaymentStatus , Timestamp from , Timestamp to);
	
	@Query("SELECT SUM(s.discount) from ProductStock s where s.productStockStatus = ?1 AND shop = ?2 AND updated >= ?3 AND updated <= ?4")
    Float getTotalDiscount(ProductStockStatus productStockStatus , Shop shop , Timestamp from , Timestamp to);
	
	@Query("SELECT SUM(s.priceSelling - IFNULL(s.discount, 0)) from ProductStock s where s.productStockStatus = ?1 AND shop = ?2 AND updated <= ?3 ")
    Float getTotalSellBeforeFromDate(ProductStockStatus productStockStatus , Shop shop , Timestamp from);
	
	List<ProductStock> findByShopAndProductStockStatusAndUpdatedBetweenOrderByUpdatedAsc(Shop shop ,ProductStockStatus productStockStatus , Timestamp from , Timestamp to);
	
	List<ProductStock> findByShopAndMerchantAndCreatedBetween(Shop shop ,Merchant merchant , Timestamp from , Timestamp to);
	
	List<ProductStock> findByShopAndMerchantAndProductStockStatusAndCreatedBetween(Shop shop ,Merchant merchant ,ProductStockStatus productStockStatus, Timestamp from , Timestamp to);

	// Merchant report query Start ///////////////////////////////////
	
	@Query("SELECT COUNT(s.id) from ProductStock s where shop = ?1  AND created >= ?2 AND created <= ?3 AND merchant = ?4")
	BigDecimal getTotalProductCountMerchantWise( Shop shop  , Timestamp from , Timestamp to , Merchant merchant);
	
	@Query("SELECT COUNT(s.id) from ProductStock s where shop = ?1  AND created >= ?2 AND created <= ?3 AND productStockStatus = ?4 AND merchant = ?5 ")
	BigDecimal getTotalAvailableProductCountMerchantWise( Shop shop  , Timestamp from , Timestamp to ,ProductStockStatus productStockStatus , Merchant merchant);
	
	@Query("SELECT COUNT(s.id) from ProductStock s where shop = ?1  AND created <= ?2 AND merchant = ?3 ")
	BigDecimal getTotalProductCountMerchantWiseOnlyToDate( Shop shop  , Timestamp to , Merchant merchant);
	
	@Query("SELECT COUNT(s.id) from ProductStock s where shop = ?1  AND created <= ?2  AND productStockStatus = ?3 AND merchant = ?4")
	BigDecimal getTotalAvailableProductCountMerchantWiseToDate( Shop shop  , Timestamp to ,ProductStockStatus productStockStatus , Merchant merchant);
	
	@Query("SELECT SUM(s.priceSelling) from ProductStock s where s.productStockStatus = ?1 AND shop = ?2 AND created <= ?3 AND merchant = ?4")
	BigDecimal getAvailableProductInSellingPriceMerchantWiseOnlyToDate(ProductStockStatus productStockStatus , Shop shop  , Timestamp to , Merchant merchant);
	
	@Query("SELECT SUM(s.priceSelling) from ProductStock s where shop = ?1 AND created <= ?2 AND merchant = ?3")
	BigDecimal getTotalProductInSellingPriceMerchantWiseOnlyToDate( Shop shop  , Timestamp to , Merchant merchant);
	
	@Query("SELECT SUM(s.priceSelling) from ProductStock s where s.productStockStatus = ?1 AND shop = ?2 AND created >= ?3 AND created <= ?4 AND merchant = ?5")
	BigDecimal getAvailableProductInSellingPriceMerchantWise(ProductStockStatus productStockStatus , Shop shop  ,Timestamp from , Timestamp to , Merchant merchant);
	
	@Query("SELECT SUM(s.priceSelling) from ProductStock s where shop = ?1 AND created >= ?2 AND created <= ?3 AND merchant = ?4")
	BigDecimal getTotalProductInSellingPriceMerchantWise( Shop shop  ,Timestamp from, Timestamp to , Merchant merchant);
	
	@Query("SELECT SUM(s.priceBuying) from ProductStock s where s.productStockStatus = ?1 AND shop = ?2 AND created <= ?3 AND merchant = ?4")
	BigDecimal getAvailableProductInBuyingPriceMerchantWiseOnlyToDate(ProductStockStatus productStockStatus , Shop shop  , Timestamp to , Merchant merchant);
	
	@Query("SELECT SUM(s.priceBuying) from ProductStock s where shop = ?1 AND created <= ?2 AND merchant = ?3")
	BigDecimal getTotalProductInBuyingPriceMerchantWiseOnlyToDate( Shop shop  , Timestamp to , Merchant merchant);
	
	@Query("SELECT SUM(s.priceBuying) from ProductStock s where s.productStockStatus = ?1 AND shop = ?2 AND created >= ?3 AND created <= ?4 AND merchant = ?5")
	BigDecimal getAvailableProductInBuyingPriceMerchantWise(ProductStockStatus productStockStatus , Shop shop  ,Timestamp from , Timestamp to , Merchant merchant);
	
	@Query("SELECT SUM(s.priceBuying) from ProductStock s where shop = ?1 AND created >= ?2 AND created <= ?3 AND merchant = ?4")
	BigDecimal getTotalProductInBuyingPriceMerchantWise( Shop shop  ,Timestamp from, Timestamp to , Merchant merchant);
	
	
	// Merchant report query End ///////////////////////////////////
}
