package com.codetreatise.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.codetreatise.bean.Customer;
import com.codetreatise.bean.SearchCriteria;
import com.codetreatise.specification.CustomerSpecification;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> , JpaSpecificationExecutor<Customer> {

	Customer findByName(String name);
	
	Customer findByMobile(String mobile);
	
	Optional<Customer> findById(Long id);
	
//	List<Customer> findByNameLikeAndMobileAndCreatedGreaterThanEqualAndCreatedLessThanEqual (String name, String mobile, Timestamp from , Timestamp to );
	
}
