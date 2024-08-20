package com.digitinary.usermanagement.exception;

public class PushEventToQueueFailedException extends RuntimeException{
    public PushEventToQueueFailedException(String message) {
        super(message);
    }
}
