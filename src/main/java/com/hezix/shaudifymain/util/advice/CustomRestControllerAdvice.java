package com.hezix.shaudifymain.util.advice;

import com.hezix.shaudifymain.util.annotations.CustomRestControllerAdviceAnnotation;
import com.hezix.shaudifymain.entity.web.CustomResponse;
import com.hezix.shaudifymain.util.exception.EntityNotFoundException;
import com.hezix.shaudifymain.util.exception.PasswordAndPasswordConfirmationNotEquals;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(annotations = CustomRestControllerAdviceAnnotation.class)
@RequiredArgsConstructor
public class CustomRestControllerAdvice {
    private final MeterRegistry meterRegistry;
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<CustomResponse> EntityNotFoundExceptionHandler(EntityNotFoundException ex) {
        meterRegistry.counter( "app_rest_error_count",
                        "ex", ex.getClass().getSimpleName(),
                        "type", "404")
                .increment();
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        CustomResponse customResponse = new CustomResponse(ex.getMessage(), notFound.value());
        return new ResponseEntity<>(customResponse, notFound);
    }
    @ExceptionHandler(PasswordAndPasswordConfirmationNotEquals.class)
    public ResponseEntity<CustomResponse> PasswordAndPasswordConfirmationExceptionHandler(EntityNotFoundException ex) {
        meterRegistry.counter( "app_rest_error_count",
                        "ex", ex.getClass().getSimpleName(),
                        "type", "400")
                .increment();
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        CustomResponse customResponse = new CustomResponse(ex.getMessage(), badRequest.value());
        return new ResponseEntity<>(customResponse, badRequest);
    }
}
