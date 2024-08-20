package com.digitinary.bookmanagement.service;

import com.digitinary.bookmanagement.entity.Book;
import com.digitinary.bookmanagement.exception.AlreadyExistsRecordException;
import com.digitinary.bookmanagement.exception.FailedToInsertRecordException;
import com.digitinary.bookmanagement.mapper.BookMapper;
import com.digitinary.bookmanagement.model.BookModel;
import com.digitinary.bookmanagement.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookMapper bookMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void should_call_save_of_book_to_repository() {
        BookModel bookModel = BookModel.builder().build();
        Book book =  Book.builder().build();
        when(bookMapper.toBook(bookModel)).thenReturn(book);
        bookService.createBook(bookModel);
        verify(bookRepository, times(1)).save(book);
    }
    @Test
    void should_throw_exception_when_failed_to_save_book_in_repository() {
        BookModel bookModel = BookModel.builder().build();
        Book book =  Book.builder().build();
        when(bookMapper.toBook(bookModel)).thenReturn(book);
        when(bookRepository.save(book)).thenThrow(RuntimeException.class);
        assertThrows(FailedToInsertRecordException.class, () ->  bookService.createBook(bookModel));
    }

    @Test
    void should_add_book_copy_when_book_exists() {
        Long bookId = 1L;
        Book book = new Book();
        book.setId(bookId);
        book.setQuantity(5);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        bookService.addBookCopy(bookId);
        verify(bookRepository).save(book);
        assertEquals(6, book.getQuantity());
    }

    @Test
    void should_throw_exception_when_book_does_not_exist() {
        Long bookId = 1L;

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(AlreadyExistsRecordException.class, () -> bookService.addBookCopy(bookId));
        assertEquals("no book with this id", exception.getMessage());

        // Verify that save is never called since the book doesn't exist
        verify(bookRepository, never()).save(any(Book.class));
    }


}
