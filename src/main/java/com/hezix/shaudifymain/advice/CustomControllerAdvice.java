package com.hezix.shaudifymain.advice;

import com.hezix.shaudifymain.annotations.CustomControllerAdviceAnnotation;
import com.hezix.shaudifymain.entity.web.CustomResponse;
import com.hezix.shaudifymain.exception.EntityNotFoundException;
import com.hezix.shaudifymain.exception.PasswordAndPasswordConfirmationNotEquals;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(annotations = CustomControllerAdviceAnnotation.class)
public class CustomControllerAdvice {
    @ExceptionHandler(EntityNotFoundException.class)
    public String EntityNotFoundExceptionHandler(EntityNotFoundException ex) {
        HttpStatus noContent = HttpStatus.NO_CONTENT;
        CustomResponse customResponse = new CustomResponse(ex.getMessage(), noContent.value());
        return "error/204";
    }
    @ExceptionHandler(PasswordAndPasswordConfirmationNotEquals.class)
    public String PasswordAndPasswordConfirmationExceptionHandler(EntityNotFoundException ex) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        CustomResponse customResponse = new CustomResponse(ex.getMessage(), badRequest.value());
        return "error/400";
    }
}
