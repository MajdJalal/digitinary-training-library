package com.digitinary.usermanagement.exception;

public class FailedToInsertRecordException extends RuntimeException{
    public FailedToInsertRecordException(String message) {
        super(message);
    }
}
