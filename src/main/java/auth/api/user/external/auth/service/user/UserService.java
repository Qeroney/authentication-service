package auth.api.user.external.auth.service.user;

import auth.api.user.external.auth.model.User;
import auth.api.user.external.auth.service.user.argument.CreateUserArgument;
import auth.api.user.external.auth.service.user.argument.UpdateUserArgument;

import java.util.UUID;

public interface UserService {

    User create(CreateUserArgument argument);

    User update(UUID id, UpdateUserArgument argument);

    User getExisting(UUID id);

    User resetPasswordByCode(String username);

    User updatePasswordByCode(String password, UUID code);

    void updatePassword(UUID id, String password);

    void enable(UUID id);

    void disable(UUID id);
}
