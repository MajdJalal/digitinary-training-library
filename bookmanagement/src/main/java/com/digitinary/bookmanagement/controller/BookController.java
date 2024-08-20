package com.digitinary.bookmanagement.controller;


import com.digitinary.bookmanagement.dto.ResponseDto;
import com.digitinary.bookmanagement.model.BookModel;
import com.digitinary.bookmanagement.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("library/booksManagement/v1/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("")
    public ResponseEntity<ResponseDto> createBook(@RequestBody BookModel bookModel) {
        bookService.createBook(bookModel);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(HttpStatus.CREATED.toString(),"CREATED"));

    }
    @PostMapping("{book-id}")
    public ResponseEntity<ResponseDto> addBookCopy(@PathVariable("book-id") Long bookId) {
        bookService.addBookCopy(bookId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(HttpStatus.CREATED.toString(),"CREATED"));

    }

}
