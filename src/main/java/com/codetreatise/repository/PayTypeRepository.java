package com.codetreatise.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codetreatise.bean.PayType;

@Repository
public interface PayTypeRepository extends JpaRepository<PayType, Long> {

	PayType findByType(String type);
	
	Optional<PayType> findById(Long id);
}
