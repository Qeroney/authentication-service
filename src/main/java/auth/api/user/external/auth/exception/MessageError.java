package auth.api.user.external.auth.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(staticName = "of")
@Schema(description = "Класс ошибки")
@NoArgsConstructor
public class MessageError {
    private String errorMessage;
}
