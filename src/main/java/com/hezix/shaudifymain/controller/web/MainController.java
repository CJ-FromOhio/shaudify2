package com.hezix.shaudifymain.controller.web;

import com.hezix.shaudifymain.entity.user.User;
import com.hezix.shaudifymain.util.AuthPrincipalChecker;
import com.hezix.shaudifymain.util.annotations.CustomControllerAdviceAnnotation;
import com.hezix.shaudifymain.service.user.UserService;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping()
@CustomControllerAdviceAnnotation
@RequiredArgsConstructor
public class MainController {
    private final AuthPrincipalChecker authPrincipalChecker;
    private final MeterRegistry meterRegistry;

    @GetMapping("/")
    public String main(@AuthenticationPrincipal Object principal, Model model) {
        User user = authPrincipalChecker.check(principal);
        model.addAttribute("user", user);
        this.meterRegistry.counter("mainController_count", List.of())
                .increment();
        this.meterRegistry.counter("mainController_count_by_username", List.of(Tag.of("username", user.getUsername())))
                .increment();
        return "main";
    }
}
