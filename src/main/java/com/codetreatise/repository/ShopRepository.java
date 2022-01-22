package com.codetreatise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codetreatise.bean.Shop;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {

	Shop findByName(String name);
}
