package com.github.qeroney.auth.api.user.external.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

public record AuthenticationUserExternalDto(
        @Schema(description = "Логин", requiredMode = REQUIRED)
        @NotBlank
        String username,

        @Schema(description = "Пароль", requiredMode = REQUIRED)
        @NotBlank
        String password
) {}
