package auth.api.user.internal.dto;

import auth.model.Contacts;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;
import ru.vadim.user.Role;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Value
@Builder
@Schema(description = "Обновление учетной записи")
public class UpdateInternalDto {

    @Schema(description = "Логин")
    @NotBlank
    String username;

    @NotNull
    @Schema(description = "Контакты пользователя")
    Contacts contacts;

    @Schema(description = "Доступ учетной записи")
    Role role;
}
