package com.digitinary.bookmanagement.exception;

public class FailedToInsertRecordException extends RuntimeException{
    public FailedToInsertRecordException(String message) {
        super(message);
    }
}
