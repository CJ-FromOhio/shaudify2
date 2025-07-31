package com.hezix.shaudifymain.advice;

import com.hezix.shaudifymain.util.annotations.CustomRestControllerAdviceAnnotation;
import com.hezix.shaudifymain.entity.web.CustomResponse;
import com.hezix.shaudifymain.util.exception.EntityNotFoundException;
import com.hezix.shaudifymain.util.exception.PasswordAndPasswordConfirmationNotEquals;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(annotations = CustomRestControllerAdviceAnnotation.class)
public class CustomRestControllerAdvice {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<CustomResponse> EntityNotFoundExceptionHandler(EntityNotFoundException ex) {
        HttpStatus noContent = HttpStatus.NO_CONTENT;
        CustomResponse customResponse = new CustomResponse(ex.getMessage(), noContent.value());
        return new ResponseEntity<>(customResponse, noContent);
    }
    @ExceptionHandler(PasswordAndPasswordConfirmationNotEquals.class)
    public ResponseEntity<CustomResponse> PasswordAndPasswordConfirmationExceptionHandler(EntityNotFoundException ex) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        CustomResponse customResponse = new CustomResponse(ex.getMessage(), badRequest.value());
        return new ResponseEntity<>(customResponse, badRequest);
    }
}
