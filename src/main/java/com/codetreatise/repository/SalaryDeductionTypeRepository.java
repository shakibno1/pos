package com.codetreatise.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codetreatise.bean.SalaryDeductionType;

@Repository
public interface SalaryDeductionTypeRepository extends JpaRepository<SalaryDeductionType, Long> {

	SalaryDeductionType findByType(String type);
	
	Optional<SalaryDeductionType> findById(Long id);
}
