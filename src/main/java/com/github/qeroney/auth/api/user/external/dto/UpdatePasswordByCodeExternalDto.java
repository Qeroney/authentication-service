package com.github.qeroney.auth.api.user.external.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;


@Builder
@Schema(description = "Дто смены пароля по коду")
public record UpdatePasswordByCodeExternalDto(

        @NotBlank
        @Schema(description = "Пароль", requiredMode = REQUIRED)
        String password,

        @NotNull
        @Schema(description = "Код для сбрасывания пароля", requiredMode = REQUIRED)
        UUID code
) {}
