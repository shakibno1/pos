package com.codetreatise.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codetreatise.bean.ProductStockParent;

@Repository
public interface ProductStockParentRepository extends JpaRepository<ProductStockParent, Long> {

	Optional<ProductStockParent> findById(Long id);
}
