package com.github.qeroney.auth.api.user.external.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;


@Builder
@Schema(description = "Дто сброса пароля")
public record ResetPasswordByCodeExternalDto(

        @NotBlank
        @Schema(description = "Логин", requiredMode = REQUIRED)
        String username
) {}
