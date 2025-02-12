package auth.api.user.external.auth.api.user.internal.dto;

import auth.api.user.external.auth.model.Contacts;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;
import ru.vadim.user.Role;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Value
@Builder
@Schema(description = "Учетная запись с ролью ADMIN")
public class UserInternalDto {

    @NotBlank
    @Schema(description = "Логин")
    String username;

    @Schema(description = "Активна ли запись")
    boolean enabled;

    @NotNull
    @Schema(description = "Контакты пользователя")
    Contacts contacts;

    @Schema(description = "Доступ учетной записи")
    Role role;
}
