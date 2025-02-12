package auth.service.user.argument;

import auth.model.Contacts;
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
