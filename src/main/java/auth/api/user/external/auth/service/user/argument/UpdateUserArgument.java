package auth.api.user.external.auth.service.user.argument;

import auth.api.user.external.auth.model.Contacts;
import lombok.Builder;
import lombok.Value;
import ru.vadim.user.Role;

@Value
@Builder
public class UpdateUserArgument {

    String username;

    Contacts contacts;

    Role role;
}
