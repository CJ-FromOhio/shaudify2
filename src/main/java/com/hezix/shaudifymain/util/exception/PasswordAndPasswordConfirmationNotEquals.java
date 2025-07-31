package com.hezix.shaudifymain.util.exception;

public class PasswordAndPasswordConfirmationNotEquals extends RuntimeException {
    public PasswordAndPasswordConfirmationNotEquals(String message) {
        super(message);
    }
}
