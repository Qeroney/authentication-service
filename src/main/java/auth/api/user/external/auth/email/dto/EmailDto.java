package auth.api.user.external.auth.email.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class EmailDto {
    String recipient;
    String body;
    String subject;
}
