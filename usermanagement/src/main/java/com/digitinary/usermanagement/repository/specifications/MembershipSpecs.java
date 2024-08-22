package com.digitinary.usermanagement.repository.specifications;

import com.digitinary.usermanagement.entity.Membership;
import com.digitinary.usermanagement.entity.User;
import com.digitinary.usermanagement.enums.MembershipType;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class MembershipSpecs {

    /**
     * creates a spec for matching the id identically
     * @param passedMembershipId
     * @return
     */
    public static Specification<Membership> hasId(Long passedMembershipId){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("id"), passedMembershipId);
    }

    /**
     * creates a spec for matching the membershipType identically
     * @param passedMembershipType
     * @return
     */
    public static Specification<Membership> hasMembershipType(MembershipType passedMembershipType){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("membershipType"), passedMembershipType);
    }

    /**
     * creates a spec for matching the issueDate identically
     * @param passedIssueDate
     * @return
     */
    public static Specification<Membership> hasIssueDate(LocalDate passedIssueDate){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("issueDate"), passedIssueDate );
    }

    /**
     * creates a spec for matching the expiryDate identically
     * @param passedExpiryDate
     * @return
     */
    public static Specification<Membership> hasExpiryDate(LocalDate passedExpiryDate){
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.equal(root.get("expiryDate"), passedExpiryDate);
    }

}
