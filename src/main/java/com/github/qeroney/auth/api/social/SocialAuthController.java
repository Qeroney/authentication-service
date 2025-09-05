package com.github.qeroney.auth.api.social;

import com.github.qeroney.auth.api.social.dto.SocialAuthCompleteDto;
import com.github.qeroney.auth.config.social.SocialConfig;
import com.github.qeroney.auth.service.authentication.AuthenticationWithVerificationCodeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@ConditionalOnBean(SocialConfig.class)
@RestController
@RequestMapping("/social/auth")
@RequiredArgsConstructor
public class SocialAuthController {
    private final AuthenticationWithVerificationCodeService authenticationWithVerificationCodeService;

    @Operation(description = "Войти через социальные сети по коду подтверждения")
    @PostMapping("/login")
    public OAuth2AccessToken login(@Valid @RequestBody SocialAuthCompleteDto dto) {
        return authenticationWithVerificationCodeService.loginByVerificationCode(dto.code());
    }
}
