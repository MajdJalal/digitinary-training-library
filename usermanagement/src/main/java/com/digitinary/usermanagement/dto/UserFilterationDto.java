package com.digitinary.usermanagement.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserFilterationDto {

    private Long passedUserId;
    private String passedUserName;
    private String passedUserPhoneNumber;
    private Long passedUserMembershipId;
}
