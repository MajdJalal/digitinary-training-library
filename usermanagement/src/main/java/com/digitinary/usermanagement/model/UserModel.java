package com.digitinary.usermanagement.model;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserModel {
    private String name;
    private String phoneNumber;
}
