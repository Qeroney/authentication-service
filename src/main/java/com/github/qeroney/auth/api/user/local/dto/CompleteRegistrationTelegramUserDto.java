package com.github.qeroney.auth.api.user.local.dto;

import com.github.qeroney.auth.model.UserProfile;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Builder
public record CompleteRegistrationTelegramUserDto(
        @NotBlank
        @Schema(description = "Логин", requiredMode = REQUIRED)
        String username,

        @NotBlank
        @Schema(description = "Id телеграм чата", requiredMode = REQUIRED)
        String telegramChatId,

        @NotNull
        @Schema(description = "Профиль", requiredMode = REQUIRED)
        UserProfile userProfile
) {}
