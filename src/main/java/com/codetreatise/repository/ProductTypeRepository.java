package com.codetreatise.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codetreatise.bean.ProductType;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {

	ProductType findByType(String type);
	
	Optional<ProductType> findById(Long id);
}
