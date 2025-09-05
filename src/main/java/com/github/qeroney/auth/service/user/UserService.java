package com.github.qeroney.auth.service.user;

import com.github.qeroney.auth.model.User;
import com.github.qeroney.auth.service.user.argument.CreateUserArgument;
import com.github.qeroney.auth.service.user.argument.UpdateUserArgument;

import java.util.Optional;
import java.util.UUID;

public interface UserService {

    User create(CreateUserArgument argument);

    User update(UUID id, UpdateUserArgument argument);

    User getExisting(UUID id);

    UUID createResetCode(String username);

    User updatePasswordByCode(String password, UUID code);

    void updatePassword(UUID id, String password);

    void enable(UUID id);

    void disable(UUID id);

    User getByUsername(String username);

    Optional<User> getByTelegramChatId(String telegramChatId);

    User getById(UUID id);
}
