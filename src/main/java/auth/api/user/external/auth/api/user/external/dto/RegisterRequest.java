package auth.api.user.external.auth.api.user.external.dto;

import auth.api.user.external.auth.model.Contacts;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Value
@Builder
@Schema(description = "Запрос на регистрацию пользователя")
public class RegisterRequest {

    @Schema(description = "Логин", requiredMode = REQUIRED)
    @NotBlank
    String username;

    @Schema(description = "Пароль", requiredMode = REQUIRED)
    @NotBlank
    String password;

    @NotNull
    @Schema(description = "Контакты пользователя", requiredMode = REQUIRED)
    Contacts contacts;
}
