package com.digitinary.bookmanagement.mapper;


import com.digitinary.bookmanagement.entity.Book;
import com.digitinary.bookmanagement.model.BookModel;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {
    public Book toBook(BookModel bookModel) {
        return Book.builder().name(bookModel.getName()).quantity(bookModel.getQuantity()).build();
    }
    public BookModel toBookModel(Book book) {
        return BookModel.builder().name(book.getName()).quantity(book.getQuantity()).build();
    }
}
