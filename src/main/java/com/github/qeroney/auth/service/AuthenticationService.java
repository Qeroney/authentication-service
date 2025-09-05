package com.github.qeroney.auth.service;

import org.springframework.security.oauth2.common.OAuth2AccessToken;

public interface AuthenticationService {

    /**
     * Аутентифицирует пользователя по имени и паролю и возвращает OAuth2 access-токен.
     */
    OAuth2AccessToken login(String username, String password);

    void logout();
}
