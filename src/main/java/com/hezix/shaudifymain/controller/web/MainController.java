package com.hezix.shaudifymain.controller.web;

import com.hezix.shaudifymain.entity.user.User;
import com.hezix.shaudifymain.util.AuthPrincipalChecker;
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
    private final AuthPrincipalChecker authPrincipalChecker;

    @GetMapping("/")
    public String main(@AuthenticationPrincipal Object principal, Model model) {
        User user = authPrincipalChecker.check(principal);
        model.addAttribute("user", user);
        return "main";
    }

}
