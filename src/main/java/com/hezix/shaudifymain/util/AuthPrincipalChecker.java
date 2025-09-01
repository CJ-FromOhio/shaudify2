package com.hezix.shaudifymain.util;

import com.hezix.shaudifymain.entity.user.User;
import com.hezix.shaudifymain.entity.user.dto.ReadUserDto;
import com.hezix.shaudifymain.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthPrincipalChecker {

    private final UserService userService;

    public User check(Object principal) {
        User user;
        String userinfo;
        if (principal instanceof UserDetails){
            userinfo = ((UserDetails) principal).getUsername();
            user = userService.findUserEntityByUsername(userinfo);
            return user;
        } else if (principal instanceof OidcUser) {
            userinfo = ((OidcUser) principal).getEmail();
            user = userService.findUserEntityByEmail(userinfo);
            return user;
        } else if (principal == null) {
            user = null;
            return user;
        } else {
            throw new IllegalStateException("Неподдерживаемый тип аутентификации");
        }
    }
}
