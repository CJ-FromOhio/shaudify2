package com.hezix.shaudifymain.controller.web;

import com.hezix.shaudifymain.annotations.CustomControllerAdviceAnnotation;
import com.hezix.shaudifymain.service.UserService;
import com.sun.security.auth.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping()
@CustomControllerAdviceAnnotation
@RequiredArgsConstructor
public class MainController {
    private final UserService userService;
    @GetMapping()
    public String main(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("user", userService.findUserByUsername(userDetails.getUsername()));
        return "main";
    }

}
