package auth.api.user.internal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
@Builder
@Schema(description = "Изменение пароля с доступом ADMIN")
public class UpdatePasswordInternalDto {

    @Schema(description = "Новый пароль")
    @NotBlank
    String password;
}
