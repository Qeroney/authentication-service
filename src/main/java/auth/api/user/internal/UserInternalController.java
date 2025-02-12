package auth.api.user.internal;

import auth.api.user.internal.dto.CreateUserInternalDto;
import auth.api.user.internal.dto.UpdateInternalDto;
import auth.api.user.internal.dto.UpdatePasswordInternalDto;
import auth.api.user.internal.dto.UserInternalDto;
import auth.api.user.internal.mapper.UserInternalMapper;
import auth.service.user.UserService;
import auth.service.user.argument.CreateUserArgument;
import auth.service.user.argument.UpdateUserArgument;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import ru.vadim.user.Role;

import javax.validation.Valid;

import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/internal/user")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserInternalController {

    UserService userService;

    UserInternalMapper mapper;

    @Operation(summary = "Создание учетной записи с доступом ADMIN")
    @PostMapping("create")
    @ResponseStatus(CREATED)
    public UserInternalDto create(@Valid @RequestBody CreateUserInternalDto dto) {
        CreateUserArgument argument = mapper.toCreateUserArgument(dto, Role.ADMIN);
        return mapper.toUserInternalDto(userService.create(argument));
    }

    @Operation(summary = "Обновление учетной записи у пользователя. Доступно с ролью ADMIN")
    @PostMapping("{id}/update")
    public UserInternalDto update(@PathVariable UUID id, @Valid @RequestBody UpdateInternalDto dto) {
        UpdateUserArgument argument = mapper.toUpdateUserArgument(dto, Role.ADMIN);
        return mapper.toUserInternalDto(userService.update(id, argument));
    }

    @Operation(summary = "Изменение пароля у пользователя")
    @PostMapping("{id}/update-password")
    public void updatePassword(@PathVariable UUID id, @Valid @RequestBody UpdatePasswordInternalDto dto) {
        userService.updatePassword(id, dto.getPassword());
    }

    @Operation(summary = "Разблокировать пользователя. Доступно с ролью ADMIN")
    @PostMapping("{id}/enable")
    public void enable(@PathVariable UUID id) {
        userService.enable(id);
    }

    @Operation(summary = "Заблокировать пользователя. Доступно с ролью ADMIN")
    @PostMapping("{id}/disable")
    public void disable(@PathVariable UUID id) {
        userService.disable(id);
    }

    @Operation(summary = "Получение данных пользователя. Доступно с ролью ADMIN")
    @GetMapping("{id}")
    public UserInternalDto get(@PathVariable UUID id) {
        return mapper.toUserInternalDto(userService.getExisting(id));
    }
}
