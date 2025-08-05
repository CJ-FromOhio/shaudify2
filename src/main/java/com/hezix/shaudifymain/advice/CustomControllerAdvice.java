package com.hezix.shaudifymain.advice;

import com.hezix.shaudifymain.util.annotations.CustomControllerAdviceAnnotation;
import com.hezix.shaudifymain.util.exception.EntityNotFoundException;
import com.hezix.shaudifymain.util.exception.FileUploadException;
import com.hezix.shaudifymain.util.exception.OwnershipMismatchException;
import com.hezix.shaudifymain.util.exception.PasswordAndPasswordConfirmationNotEquals;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(annotations = CustomControllerAdviceAnnotation.class)
public class CustomControllerAdvice {
    @ExceptionHandler(EntityNotFoundException.class)
    public String EntityNotFoundExceptionHandler(EntityNotFoundException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return "error/404";
    }

//    @ExceptionHandler(PasswordAndPasswordConfirmationNotEquals.class)
//    public String PasswordAndPasswordConfirmationExceptionHandler(PasswordAndPasswordConfirmationNotEquals ex) {
//        return "error/400";
//    }

    @ExceptionHandler(OwnershipMismatchException.class)
    public String OwnershipMismatchExceptionExceptionHandler(OwnershipMismatchException ex) {
        return "error/403";
    }

    @ExceptionHandler(FileUploadException.class)
    public String FileUploadExceptionExceptionHandler(FileUploadException ex) {
        return "error/400";
    }
}
