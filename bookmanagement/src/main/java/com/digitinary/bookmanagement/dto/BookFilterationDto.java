package com.digitinary.bookmanagement.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookFilterationDto {
    private Long passedBookId;
    private String passedBookName;
    private Integer passedBookQuantity;
}
