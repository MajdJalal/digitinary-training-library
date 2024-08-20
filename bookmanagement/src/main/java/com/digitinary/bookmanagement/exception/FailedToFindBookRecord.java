package com.digitinary.bookmanagement.exception;

public class FailedToFindBookRecord extends RuntimeException{
    public FailedToFindBookRecord(String message) {
        super(message);
    }
}
