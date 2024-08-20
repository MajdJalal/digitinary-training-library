package com.digitinary.bookmanagement.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    private Long id;
    private String name;
    private int quantity;


    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Long getId() {
        return id;
    }

    @Column(name = "book_name")
    public String getName() {
        return name;
    }

    @Column(name = "book_quantity")
    public int getQuantity() {
        return quantity;
    }
}
