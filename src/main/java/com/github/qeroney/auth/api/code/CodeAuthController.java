package com.github.qeroney.auth.api.code;

import com.github.qeroney.auth.action.code.CreateVerificationCodeAction;
import com.github.qeroney.auth.api.code.dto.AuthCompleteCodeDto;
import com.github.qeroney.auth.service.authentication.AuthenticationWithVerificationCodeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("code")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CodeAuthController {
    AuthenticationWithVerificationCodeService authenticationWithVerificationCodeService;
    CreateVerificationCodeAction createVerificationCodeAction;

    @Operation(description = "Создание временного кода авторизации")
    @GetMapping("/init")
    public UUID init() {
        return createVerificationCodeAction.execute();
    }

    @PostMapping("/complete")
    @Operation(description = "Завершение авторизации по коду")
    public OAuth2AccessToken complete(@RequestBody AuthCompleteCodeDto dto) {
        return authenticationWithVerificationCodeService.loginByVerificationCode(dto.code());
    }
}
