package auth.api.user.external.auth.model;

import lombok.*;

import javax.persistence.Embeddable;
import javax.validation.constraints.Email;

import static lombok.AccessLevel.PRIVATE;

@Embeddable
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
public class Contacts {

    @Email
    String email;

    String phone;
}
