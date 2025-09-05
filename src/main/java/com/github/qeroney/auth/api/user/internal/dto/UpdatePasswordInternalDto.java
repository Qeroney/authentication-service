package com.github.qeroney.auth.api.user.internal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import javax.validation.constraints.NotBlank;

@Builder
@Schema(description = "Изменение пароля с доступом ADMIN")
public record UpdatePasswordInternalDto(

        @Schema(description = "Новый пароль")
        @NotBlank
        String password
) {}
