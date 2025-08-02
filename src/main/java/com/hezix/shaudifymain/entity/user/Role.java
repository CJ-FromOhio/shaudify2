package com.hezix.shaudifymain.entity.user;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER("ПОЛЬЗОВАТЕЛЬ"), ADMIN("АДМИН"), AUTHOR("АВТОР");

    Role(String name) {
    }

    @Override
    public String getAuthority() {
        return name();
    }

}
