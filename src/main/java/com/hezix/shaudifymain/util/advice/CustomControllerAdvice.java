package com.hezix.shaudifymain.util.advice;

import com.hezix.shaudifymain.util.annotations.CustomControllerAdviceAnnotation;
import com.hezix.shaudifymain.util.exception.EntityNotFoundException;
import com.hezix.shaudifymain.util.exception.FileUploadException;
import com.hezix.shaudifymain.util.exception.OwnershipMismatchException;
import com.hezix.shaudifymain.util.exception.PasswordAndPasswordConfirmationNotEquals;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice(annotations = CustomControllerAdviceAnnotation.class)
@RequiredArgsConstructor
public class CustomControllerAdvice {
    private final MeterRegistry meterRegistry;

    @ExceptionHandler(EntityNotFoundException.class)
    public String EntityNotFoundExceptionHandler(EntityNotFoundException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        meterRegistry.counter( "app_error_count",
                "ex", ex.getClass().getSimpleName(),
                "type", "404")
                .increment();
        return "error/404";
    }

    @ExceptionHandler(OwnershipMismatchException.class)
    public String OwnershipMismatchExceptionExceptionHandler(OwnershipMismatchException ex) {
        meterRegistry.counter( "app_error_count",
                        "ex", ex.getClass().getSimpleName(),
                        "type", "403")
                .increment();
        return "error/403";
    }

    @ExceptionHandler(FileUploadException.class)
    public String FileUploadExceptionExceptionHandler(FileUploadException ex) {
        meterRegistry.counter( "app_error_count",
                        "ex", ex.getClass().getSimpleName(),
                        "type", "400")
                .increment();
        return "error/400";
    }
}
