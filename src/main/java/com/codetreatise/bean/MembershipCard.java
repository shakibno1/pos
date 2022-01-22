package com.codetreatise.bean;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="membershipCard")
public class MembershipCard extends BaseModel {

	private Float discount;
	private MembershipCardType membershipCardType;
	private Customer customer;
    private String membershipCardNumber;

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    @Enumerated(EnumType.STRING)
    public MembershipCardType getMembershipCardType() {
        return membershipCardType;
    }

    public void setMembershipCardType(MembershipCardType membershipCardType) {
        this.membershipCardType = membershipCardType;
    }
    @ManyToOne
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getMembershipCardNumber() {
        return membershipCardNumber;
    }

    public void setMembershipCardNumber(String membershipCardNumber) {
        this.membershipCardNumber = membershipCardNumber;
    }
}
