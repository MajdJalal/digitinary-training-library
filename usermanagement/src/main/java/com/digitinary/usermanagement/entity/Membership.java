package com.digitinary.usermanagement.entity;


import com.digitinary.usermanagement.enums.MembershipType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Builder
@Entity
@Table(name = "memberships")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Membership {

    private Long id;
    private MembershipType membershipType;

    private LocalDate issueDate;
    private LocalDate expiryDate;

    @Id
    @Column(name = "membership_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Long getId() {
        return id;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "membershiptype")
    public MembershipType getMembershipType() {
        return membershipType;
    }

    @Column(name = "membership_issueDate")
    public LocalDate getIssueDate() {
        return issueDate;
    }

    @Column(name = "membership_expiryDate")
    public LocalDate getExpiryDate() {
        return expiryDate;
    }
}
