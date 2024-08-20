package com.digitinary.bookmanagement.model;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BookModel {

    private String name;
    private int quantity;
}
