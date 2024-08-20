package com.digitinary.usermanagement.repository;

import com.digitinary.usermanagement.entity.Membership;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipRepository extends JpaRepository<Membership, Long> {
}
