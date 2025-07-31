package com.hezix.shaudifymain.controller.web;

import com.hezix.shaudifymain.util.annotations.CustomControllerAdviceAnnotation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@CustomControllerAdviceAnnotation
public class SecurityController {

    @GetMapping("/login")
    public String login() {
        return "users/login";
    }
}
