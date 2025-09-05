package com.github.qeroney.auth.api.user.external.dto;

import com.github.qeroney.auth.model.UserProfile;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;


@Builder
@Schema(description = "Запрос на регистрацию пользователя")
public record CompleteRegistrationExternalDto(

        @Schema(description = "Логин", requiredMode = REQUIRED)
        @NotBlank
        String username,

        @Schema(description = "Пароль", requiredMode = REQUIRED)
        @NotBlank
        String password,

        @NotNull
        @Schema(description = "Профиль", requiredMode = REQUIRED)
        UserProfile userProfile
) {}
