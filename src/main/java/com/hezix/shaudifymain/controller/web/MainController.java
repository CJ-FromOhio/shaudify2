package com.hezix.shaudifymain.controller.web;

import com.hezix.shaudifymain.annotations.CustomControllerAdviceAnnotation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping()
@CustomControllerAdviceAnnotation
public class MainController {
    @GetMapping()
    public String main() {
        return "main";
    }

}
