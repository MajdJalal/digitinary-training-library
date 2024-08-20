package com.digitinary.bookmanagement.service;


import com.digitinary.bookmanagement.dto.ReservationDto;
import com.digitinary.bookmanagement.entity.Book;
import com.digitinary.bookmanagement.exception.AlreadyExistsRecordException;
import com.digitinary.bookmanagement.exception.FailedToFindBookRecord;
import com.digitinary.bookmanagement.exception.FailedToInsertRecordException;
import com.digitinary.bookmanagement.mapper.BookMapper;
import com.digitinary.bookmanagement.model.BookModel;
import com.digitinary.bookmanagement.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookService {

    private final Logger logger = LoggerFactory.getLogger(BookService.class);

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookService(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    /**
     * create a new book
     * @param bookModel
     * @throws FailedToInsertRecordException
     * @author Majd Alkhawaja
     */
    public void createBook(BookModel bookModel) {
        Book book = bookMapper.toBook(bookModel);
        try{
            bookRepository.save(book);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new FailedToInsertRecordException("failed to create a new book");
        }
    }

    /**
     * add a new copy of an existing book
     * @param bookId
     * @throws  AlreadyExistsRecordException
     * @author Majd Alkhawaja
     */
    public void addBookCopy(Long bookId) {
        //TO-DO
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if(optionalBook.isEmpty()) throw new AlreadyExistsRecordException("no book with this id");
        Book book = optionalBook.get();
        book.setQuantity(book.getQuantity() + 1);
        bookRepository.save(book);
    }

    /**
     * reads a reservationDto from a queue when available and reserve an order based on it
     * @param reservationDto
     * @throws FailedToFindBookRecord
     * @author Majd Alkhawaja
     */
    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void reserveBook(ReservationDto reservationDto) {
        logger.info("Received message: " + reservationDto.getBookName() + " " + reservationDto.getQuantity());

        Optional<Book> optionalBook = bookRepository.findByName(reservationDto.getBookName());
        if(optionalBook.isEmpty()) throw new FailedToFindBookRecord("no book with this name");
        Book book = optionalBook.get();
        book.setQuantity(book.getQuantity() - 1);
        bookRepository.save(book);
    }

}
