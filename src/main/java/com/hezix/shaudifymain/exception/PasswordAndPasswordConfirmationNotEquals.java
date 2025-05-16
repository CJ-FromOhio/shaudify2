package com.hezix.shaudifymain.exception;

public class PasswordAndPasswordConfirmationNotEquals extends RuntimeException {
    public PasswordAndPasswordConfirmationNotEquals(String message) {
        super(message);
    }
}
