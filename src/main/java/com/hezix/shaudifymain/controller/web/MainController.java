package com.hezix.shaudifymain.controller.web;

import com.hezix.shaudifymain.util.annotations.CustomControllerAdviceAnnotation;
import com.hezix.shaudifymain.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
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

    @GetMapping("/")
    public String main(@AuthenticationPrincipal Object principal, Model model) {
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else if (principal instanceof OidcUser) {
            username = ((OidcUser) principal).getEmail();
        } else {
            throw new IllegalStateException("Неподдерживаемый тип аутентификации");
        }

        model.addAttribute("user", userService.findUserByUsername(username));
        return "main";
    }

}
