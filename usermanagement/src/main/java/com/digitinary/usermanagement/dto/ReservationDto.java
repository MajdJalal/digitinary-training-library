package com.digitinary.usermanagement.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReservationDto {
    private String bookName;
    private String quantity;
}
