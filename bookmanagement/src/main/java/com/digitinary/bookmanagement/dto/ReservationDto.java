package com.digitinary.bookmanagement.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationDto {
    private String bookName;
    private String quantity;
}
