package com.github.qeroney.auth.api.user.local;

import com.github.qeroney.auth.action.user.CompleteRegistrationTelegramUserAction;
import com.github.qeroney.auth.action.user.GetAuthenticatedUserAction;
import com.github.qeroney.auth.api.user.local.dto.CompleteRegistrationTelegramUserDto;
import com.github.qeroney.auth.api.user.local.dto.UserDto;
import com.github.qeroney.auth.api.user.local.mapper.LocalUserMapper;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("local/user")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class LocalUserController {

    LocalUserMapper localUserMapper;
    GetAuthenticatedUserAction getAuthenticatedUserAction;
    CompleteRegistrationTelegramUserAction completeRegistrationTelegramUserAction;

    @Operation(description = "Регистрирует пользователя, возвращает id пользователя в системе")
    @PostMapping("register-telegram")
    public UUID registration(@RequestBody CompleteRegistrationTelegramUserDto dto) {
        return completeRegistrationTelegramUserAction.execute(localUserMapper.toArgument(dto));
    }

    @Operation(description = "Возвращает данные о текущем аутентифицированном пользователе")
    @GetMapping("/me")
    public UserDto get() {
        return localUserMapper.toDto(getAuthenticatedUserAction.execute());
    }
}
