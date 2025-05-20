package com.hezix.shaudifymain.advice;

import com.hezix.shaudifymain.annotations.CustomControllerAdviceAnnotation;
import com.hezix.shaudifymain.annotations.CustomRestControllerAdviceAnnotation;
import com.hezix.shaudifymain.entity.web.CustomResponse;
import com.hezix.shaudifymain.exception.EntityNotFoundException;
import com.hezix.shaudifymain.exception.PasswordAndPasswordConfirmationNotEquals;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@ControllerAdvice(annotations = CustomControllerAdviceAnnotation.class)
public class CustomControllerAdvice {
    @ExceptionHandler(EntityNotFoundException.class)
    public String EntityNotFoundExceptionHandler(EntityNotFoundException ex) {
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        CustomResponse customResponse = new CustomResponse(ex.getMessage(), notFound.value());
        return "errors/404_Not_Found";
    }
    @ExceptionHandler(PasswordAndPasswordConfirmationNotEquals.class)
    public String PasswordAndPasswordConfirmationExceptionHandler(EntityNotFoundException ex) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        CustomResponse customResponse = new CustomResponse(ex.getMessage(), badRequest.value());
        return "errors/400_Bad_Request";
    }
}
