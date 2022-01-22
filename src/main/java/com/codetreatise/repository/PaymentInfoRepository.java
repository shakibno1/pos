package com.codetreatise.repository;

import com.codetreatise.bean.PaymentInfo;
import com.codetreatise.bean.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentInfoRepository extends JpaRepository<PaymentInfo, Long> , JpaSpecificationExecutor<PaymentInfo> {

	PaymentInfo findByShopAndYearAndMonth(Shop shop,String year,String month);

	List<PaymentInfo> findByShopAndYearAndMonthAndPaymentStatus(Shop shop,String year,String month,Boolean paymentStatus);

	List<PaymentInfo> findByShopAndPaymentStatus(Shop shop, Boolean paymentStatus);

	Optional<PaymentInfo> findById(Long id);

}
