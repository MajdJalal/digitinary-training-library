package com.digitinary.usermanagement.mapper;

import com.digitinary.usermanagement.entity.Membership;
import com.digitinary.usermanagement.model.MembershipModel;
import jakarta.persistence.Column;
import org.springframework.stereotype.Component;

@Component
public class MembershipMapper {
    public Membership toMembership(MembershipModel membershipModel) {
        return Membership.builder().membershipType(membershipModel.getMembershipType())
                .issueDate(membershipModel.getIssueDate())
                .expiryDate(membershipModel.getExpiryDate())
                .build();
    }
    public MembershipModel toMembershipModel(Membership membership) {
        return MembershipModel.builder().membershipType(membership.getMembershipType())
                .issueDate(membership.getIssueDate())
                .expiryDate(membership.getExpiryDate())
                .build();
    }
}
