package com.github.qeroney.auth.api.user.internal.dto;

import com.github.qeroney.auth.model.UserProfile;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Builder
@Schema(description = "Обновление учетной записи")
public record UpdateInternalDto(

        @Schema(description = "Логин")
        @NotBlank
        String username,

        @NotBlank
        @Schema(description = "Доступ учетной записи")
        Set<String> authorities,

        @NotNull
        @Schema(description = "Профиль", requiredMode = REQUIRED)
        UserProfile userProfile
) {}
