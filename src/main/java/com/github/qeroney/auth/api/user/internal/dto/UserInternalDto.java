package com.github.qeroney.auth.api.user.internal.dto;

import com.github.qeroney.auth.model.UserProfile;
import com.github.qeroney.auth.model.UserType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;


@Builder
@Schema(description = "Учетная запись с ролью ADMIN")
public record UserInternalDto(

        @NotBlank
        @Schema(description = "Логин", requiredMode = REQUIRED)
        String username,

        @Schema(description = "Активна ли запись", requiredMode = REQUIRED)
        boolean enabled,

        @NotBlank
        @Schema(description = "Доступ учетной записи", requiredMode = REQUIRED)
        Set<String> authorities,

        @NotNull
        @Schema(description = "Профиль", requiredMode = REQUIRED)
        UserProfile userProfile,

        @NotBlank
        @Schema(description = "Профиль", requiredMode = REQUIRED)
        UserType userType
) {}
