package com.hezix.shaudifymain.advice;

import com.hezix.shaudifymain.annotations.CustomControllerAdviceAnnotation;
import com.hezix.shaudifymain.entity.web.CustomResponse;
import com.hezix.shaudifymain.exception.EntityNotFoundException;
import com.hezix.shaudifymain.exception.FileUploadException;
import com.hezix.shaudifymain.exception.OwnershipMismatchException;
import com.hezix.shaudifymain.exception.PasswordAndPasswordConfirmationNotEquals;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(annotations = CustomControllerAdviceAnnotation.class)
public class CustomControllerAdvice {
    @ExceptionHandler(EntityNotFoundException.class)
    public String EntityNotFoundExceptionHandler(EntityNotFoundException ex) {
        return "error/204";
    }
    @ExceptionHandler(PasswordAndPasswordConfirmationNotEquals.class)
    public String PasswordAndPasswordConfirmationExceptionHandler(PasswordAndPasswordConfirmationNotEquals ex) {
        return "error/400";
    }
    @ExceptionHandler(OwnershipMismatchException.class)
    public String OwnershipMismatchExceptionExceptionHandler(OwnershipMismatchException ex) {
        return "error/403";
    }
    @ExceptionHandler(FileUploadException.class)
    public String FileUploadExceptionExceptionHandler(FileUploadException ex) {
        return "error/400";
    }
}
