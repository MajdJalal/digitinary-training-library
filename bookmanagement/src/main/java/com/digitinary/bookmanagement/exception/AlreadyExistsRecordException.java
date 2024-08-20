package com.digitinary.bookmanagement.exception;

public class AlreadyExistsRecordException extends RuntimeException{
    public AlreadyExistsRecordException(String message) {
        super(message);
    }
}
