package auth.api.user.external.auth.api.user.external.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Value
@Builder
@Schema(description = "Ответ на аутентификацию, содержащий access и refresh токены")
public class AuthResponse {

    @Schema(description = "Access токен", requiredMode = REQUIRED, example = "eyJhbGciOiJIUzI1NiIsInR...")
    String accessToken;

    @Schema(description = "Refresh токен", requiredMode = REQUIRED, example = "dGhpc19pc19hX3JlZnJlc2g...")
    String refreshToken;
}
