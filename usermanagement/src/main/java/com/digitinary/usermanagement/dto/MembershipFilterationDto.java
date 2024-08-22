package com.digitinary.usermanagement.dto;

import com.digitinary.usermanagement.enums.MembershipType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
public class MembershipFilterationDto {

    private Long passedMembershipId;
    private MembershipType passedMembershipType;

    private LocalDate passedIssueDate;
    private LocalDate passedExpiryDate;
}
