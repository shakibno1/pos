package com.codetreatise.service;

import com.codetreatise.bean.PaymentInfo;
import com.codetreatise.bean.Shop;
import com.codetreatise.generic.GenericService;

import java.util.List;


public interface PaymentInfoService extends GenericService<PaymentInfo> {

	PaymentInfo findByShopAndYearAndMonth(Shop shop,String year,String month);

	List<PaymentInfo> findByShopAndYearAndMonthAndPaymentStatus(Shop shop,String year,String month,Boolean paymentStatus);

	List<PaymentInfo> findByShopAndPaymentStatus(Shop shop, Boolean paymentStatus);

	PaymentInfo findById(Long id);

}
