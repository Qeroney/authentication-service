package auth.api.user.external.auth.service.user.argument;

import auth.api.user.external.auth.model.Contacts;
import lombok.Builder;
import lombok.Value;
import ru.vadim.user.Role;

@Value
@Builder
public class CreateUserArgument {

    String username;

    String password;

    Contacts contacts;

    Role role;
}
