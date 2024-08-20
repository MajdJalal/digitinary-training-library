package com.digitinary.bookmanagement.exception;

public class PushEventToQueueFailedException extends RuntimeException{
    public PushEventToQueueFailedException(String message) {
        super(message);
    }
}
