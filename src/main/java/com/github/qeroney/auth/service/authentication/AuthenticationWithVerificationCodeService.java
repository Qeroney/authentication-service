package com.github.qeroney.auth.service.authentication;

import org.springframework.security.oauth2.common.OAuth2AccessToken;

import java.util.UUID;

public interface AuthenticationWithVerificationCodeService {

    UUID createVerificationCode(String username);

    OAuth2AccessToken loginByVerificationCode(UUID code);

    String getByUsernameByCode(UUID code);
}
