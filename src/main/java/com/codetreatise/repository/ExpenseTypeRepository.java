package com.codetreatise.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codetreatise.bean.ExpenseType;

@Repository
public interface ExpenseTypeRepository extends JpaRepository<ExpenseType, Long> {

	ExpenseType findByType(String type);
	
	Optional<ExpenseType> findById(Long id);
}
