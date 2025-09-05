package com.github.qeroney.auth.api.user.local.dto;

import com.github.qeroney.auth.model.UserProfile;
import com.github.qeroney.auth.model.UserType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Builder
public record UserDto(

        @NotBlank
        @Schema(description = "Логин", requiredMode = REQUIRED)
        String username,

        @NotBlank
        @Schema(description = "Доступ учетной записи", requiredMode = REQUIRED)
        Set<String> authorities,

        @NotNull
        @Schema(description = "Профиль", requiredMode = REQUIRED)
        UserProfile userProfile,

        @NotBlank
        @Schema(description = "Тип аккаунта", requiredMode = REQUIRED)
        UserType userType,

        @Schema(description = "Чат айди пользователя в телеге", requiredMode = NOT_REQUIRED)
        String telegramChatId
) {}
