package com.digitinary.usermanagement.repository.specifications;

import com.digitinary.usermanagement.entity.Membership;
import com.digitinary.usermanagement.entity.User;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecs {


    /**
     * creates a spec for matching the id identically
     * @param passedUserId
     * @return
     */
    public static Specification<User> hasId(Long passedUserId){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("id"), passedUserId);
    }

    /**
     * creates a spec to match a like name
     * @param passedUserName
     * @return
     */
    public static Specification<User> containsName(String passedUserName){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + passedUserName.toLowerCase() + "%");
    }

    /**
     * creates a spec to match a like phoneNumber
     * @param passedUserPhoneNumber
     * @return
     */
    public static Specification<User> containsPhoneNumber(String passedUserPhoneNumber){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("phoneNumber"), "%" + passedUserPhoneNumber + "%");
    }
    public static Specification<User> hasMembershipId(Long passedUserMembershipId){

            return (root, query, criteriaBuilder) ->{
                Join<User, Membership> userMembershipJoin = root.join("membership", JoinType.INNER);
                criteriaBuilder.equal(userMembershipJoin.get("id"), passedUserMembershipId);
                return null;
            };

    }

}
