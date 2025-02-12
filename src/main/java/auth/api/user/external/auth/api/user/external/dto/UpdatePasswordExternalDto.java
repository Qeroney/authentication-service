package auth.api.user.external.auth.api.user.external.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Value
@Builder
@Schema(description = "Дто смены пароля")
public class UpdatePasswordExternalDto {

    @NotBlank
    @Schema(description = "Пароль", requiredMode = REQUIRED)
    String password;
}
