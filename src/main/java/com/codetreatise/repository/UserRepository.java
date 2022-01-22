package com.codetreatise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codetreatise.bean.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByUserName(String username);
	
	User findByName(String name);
}
