package auth.api.user.external;

import auth.action.user.AuthenticationUserAction;
import auth.action.user.RegistrationUserAction;
import auth.action.user.ResetPasswordAction;
import auth.action.user.UpdatePasswordAction;
import auth.action.user.argument.UpdatePasswordActionArgument;
import auth.api.user.external.dto.*;
import auth.api.user.external.mapper.UserExternalMapper;
import auth.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/external/user")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserExternalController {

    ResetPasswordAction resetPasswordAction;

    UpdatePasswordAction updatePasswordAction;

    UserExternalMapper userMapper;

    AuthenticationUserAction authenticationUserAction;

    RegistrationUserAction registrationUserAction;

    UserService userService;

    @Operation(summary = "Создание кода для изменения пароля")
    @PostMapping("reset-password-by-code")
    public void resetPasswordByCode(@Valid @RequestBody ResetPasswordByCodeExternalDto dto) {
        resetPasswordAction.execute(dto.getUsername());
    }

    @Operation(summary = "Изменение пароля по коду")
    @PostMapping("update-password-by-code")
    public AuthResponse updatePasswordByCode(@Valid @RequestBody UpdatePasswordByCodeExternalDto dto) {
        UpdatePasswordActionArgument argument = userMapper.toUpdatePasswordArgument(dto);
        return updatePasswordAction.execute(argument);
    }

    @Operation(summary = "Изменение пароля")
    @PostMapping("{id}/update-password")
    public void updatePassword(@PathVariable UUID id, @Valid @RequestBody UpdatePasswordExternalDto dto) {
        userService.updatePassword(id, dto.getPassword());
    }

    @Operation(summary = "Создание учетной записи пользователя с последующей выдачей токенов доступа")
    @PostMapping("register")
    public ResponseEntity<AuthResponse> registration(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(registrationUserAction.execute(request));
    }

    @Operation(summary = "Повторный вход в систему, обновление токенов доступа")
    @PostMapping("login")
    public ResponseEntity<AuthResponse> authentication(@Valid @RequestBody AuthRequest request) {
        return ResponseEntity.ok(authenticationUserAction.execute(request));
    }
}
