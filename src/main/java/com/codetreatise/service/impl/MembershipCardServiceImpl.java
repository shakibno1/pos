package com.codetreatise.service.impl;

import com.codetreatise.bean.*;
import com.codetreatise.repository.MembershipCardRepository;
import com.codetreatise.service.MembershipCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MembershipCardServiceImpl implements MembershipCardService {

	@Autowired
	private MembershipCardRepository repository;

	@Override
	public MembershipCard save(MembershipCard entity) {
		return repository.save(entity);
	}

	@Override
	public MembershipCard update(MembershipCard entity) {
		return repository.save(entity);
	}

	@Override
	public void delete(MembershipCard entity) {
		repository.delete(entity);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	public List<MembershipCard> findAll() {
		return repository.findAll();
	}

	@Override
	public void deleteInBatch(List<MembershipCard> membershipCards) {
		repository.deleteInBatch(membershipCards);
	}


	@Override
	public MembershipCard find(Long id) {
		Optional<MembershipCard> membershipCardOptional = repository.findById(id);
		if (membershipCardOptional.isPresent()){
			MembershipCard membershipCard = membershipCardOptional.get();
			return membershipCard;
		}
		else{
		   return null;
		}
	}

	@Override
	public List<MembershipCard> findByCustomer(Customer customer) {
		return repository.findByCustomer(customer);
	}

	@Override
	public List<MembershipCard> findByMembershipCardNumber(String membershipCardNumber) {
		return repository.findByMembershipCardNumber(membershipCardNumber);
	}
}
