package com.codetreatise.specification;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.codetreatise.bean.Customer;
import com.codetreatise.bean.SearchCriteria;

public class CustomerSpecification implements Specification<Customer>  {

	private SearchCriteria criteria;
	 
    public CustomerSpecification(SearchCriteria criteria) {
		super();
		this.criteria = criteria;
	}
    
	public SearchCriteria getCriteria() {
		return criteria;
	}

	@Override
    public Predicate toPredicate
      (Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
  
        if (criteria.getOperation().equals(">") && criteria.getValue()!=null) {
            return builder.greaterThanOrEqualTo(
              root.<Date> get(criteria.getKey()), (Date)criteria.getValue());
        } 
        else if (criteria.getOperation().equals("<") && criteria.getValue()!=null) {
            return builder.lessThanOrEqualTo(
              root.<Date> get(criteria.getKey()), (Date) criteria.getValue());
        } 
        else if (criteria.getOperation().equals("=") && criteria.getValue()!=null) {
            return builder.equal(
              root.<String> get(criteria.getKey()), criteria.getValue().toString());
        } 
        else if (criteria.getOperation().equals("%") && criteria.getValue()!=null) {
            return builder.like(
              root.<String> get(criteria.getKey()), criteria.getValue().toString());
        } 
        else if (criteria.getOperation().equals(":")) {
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                return builder.like(
                  root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
            } else {
                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
            }
        }
        return null;
    }
}
