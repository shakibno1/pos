package com.codetreatise.service;

import com.codetreatise.bean.*;
import com.codetreatise.generic.GenericService;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public interface MembershipCardService extends GenericService<MembershipCard> {

	List<MembershipCard> findByCustomer(Customer customer);

	List<MembershipCard> findByMembershipCardNumber(String membershipCardNumber);
}
