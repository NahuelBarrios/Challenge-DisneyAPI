package com.disneyAPI.security;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    public static MainUser getMainUser() {
        MainUser mainUser = (MainUser) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        return mainUser;
    }
}
