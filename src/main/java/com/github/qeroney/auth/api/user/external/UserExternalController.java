package com.github.qeroney.auth.api.user.external;

import com.github.qeroney.auth.action.user.AuthenticationUserAction;
import com.github.qeroney.auth.action.user.CompleteRegistrationUserAction;
import com.github.qeroney.auth.action.user.ResetPasswordAction;
import com.github.qeroney.auth.action.user.UpdatePasswordAction;
import com.github.qeroney.auth.action.user.argument.UpdatePasswordActionArgument;
import com.github.qeroney.auth.api.user.external.dto.AuthenticationUserExternalDto;
import com.github.qeroney.auth.api.user.external.dto.CompleteRegistrationExternalDto;
import com.github.qeroney.auth.api.user.external.dto.ResetPasswordByCodeExternalDto;
import com.github.qeroney.auth.api.user.external.dto.UpdatePasswordByCodeExternalDto;
import com.github.qeroney.auth.api.user.external.mapper.UserExternalMapper;
import com.github.qeroney.auth.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("external/user")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserExternalController {

    ResetPasswordAction resetPasswordAction;
    UpdatePasswordAction updatePasswordAction;
    UserExternalMapper userMapper;
    CompleteRegistrationUserAction completeRegistrationUserAction;
    AuthenticationService authenticationService;
    AuthenticationUserAction authenticationUserAction;

    @Operation(summary = "Создание кода для изменения пароля")
    @PostMapping("reset-password-by-code")
    public void resetPasswordByCode(@Valid @RequestBody ResetPasswordByCodeExternalDto dto) {
        resetPasswordAction.execute(dto.username());
    }

    @Operation(summary = "Изменение пароля по коду")
    @PostMapping("update-password-by-code")
    public OAuth2AccessToken updatePasswordByCode(@Valid @RequestBody UpdatePasswordByCodeExternalDto dto) {
        UpdatePasswordActionArgument argument = userMapper.toUpdatePasswordArgument(dto);
        return updatePasswordAction.execute(argument);
    }

    @Operation(summary = "Создание учетной записи пользователя с последующей выдачей токенов доступа")
    @PostMapping("register")
    public OAuth2AccessToken registration(@Valid @RequestBody CompleteRegistrationExternalDto dto) {
        return completeRegistrationUserAction.execute(userMapper.toCompleteRegistrationArgument(dto));
    }

    @Operation(summary = "Повторный вход в систему")
    @PostMapping("login")
    public OAuth2AccessToken login(@Valid @RequestBody AuthenticationUserExternalDto dto) {
       return authenticationUserAction.execute(userMapper.toAuthenticationArgument(dto));
    }

    @Operation(summary = "Выход с последующим очищением токенов и контекста")
    @GetMapping("/logout")
    public void logout() {
        authenticationService.logout();
    }
}
