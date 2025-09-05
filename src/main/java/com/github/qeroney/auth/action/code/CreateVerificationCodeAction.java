package com.github.qeroney.auth.action.code;

import com.github.qeroney.auth.action.ActionWithoutArg;
import com.github.qeroney.auth.service.authentication.AuthenticationWithVerificationCodeService;
import io.github.qeroney.security.core.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CreateVerificationCodeAction implements ActionWithoutArg<UUID> {

    private final AuthenticationWithVerificationCodeService authenticationWithVerificationCodeService;
    private final AuthService authService;

    @Override
    public UUID execute() {
        String username = authService.getAuthorizedUserDetails().getUsername();
        return authenticationWithVerificationCodeService.createVerificationCode(username);
    }
}
