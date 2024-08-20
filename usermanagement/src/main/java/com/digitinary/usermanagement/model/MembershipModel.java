package com.digitinary.usermanagement.model;

import com.digitinary.usermanagement.enums.MembershipType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Builder
@Getter
@Setter
public class MembershipModel {
    private MembershipType membershipType;

    private LocalDate issueDate;
    private LocalDate expiryDate;
}
