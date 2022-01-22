package com.codetreatise.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codetreatise.bean.Employee;
import com.codetreatise.bean.Shop;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	Employee findByName(String name);
	
	Optional<Employee> findById(Long id);
	
	List<Employee> findByShop (Shop shop);

	List<Employee> findByShopAndStatus (Shop shop,Boolean status);
}
