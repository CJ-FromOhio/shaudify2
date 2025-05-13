package com.hezix.shaudifymain.controller.web;

import com.hezix.shaudifymain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping()
    public String get(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "users/all_users";
    }
}
