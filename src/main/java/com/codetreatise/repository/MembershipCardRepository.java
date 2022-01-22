package com.codetreatise.repository;

import com.codetreatise.bean.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Repository
public interface MembershipCardRepository extends JpaRepository<MembershipCard, Long> {

	List<MembershipCard> findByCustomer(Customer customer);

	List<MembershipCard> findByMembershipCardNumber(String membershipCardNumber);
}
