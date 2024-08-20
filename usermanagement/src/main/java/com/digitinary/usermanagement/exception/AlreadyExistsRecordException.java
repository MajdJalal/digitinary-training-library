package com.digitinary.usermanagement.exception;

public class AlreadyExistsRecordException extends RuntimeException{
    public AlreadyExistsRecordException(String message) {
        super(message);
    }
}
