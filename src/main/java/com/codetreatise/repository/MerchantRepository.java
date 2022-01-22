package com.codetreatise.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codetreatise.bean.Merchant;
import com.codetreatise.bean.ProductType;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long> {

	Merchant findByName(String name);

	Optional<Merchant> findById(Long id);

	List<Merchant> findByProductType(ProductType productType);
}
