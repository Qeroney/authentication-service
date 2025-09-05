package com.github.qeroney.auth.action.user;

import com.github.qeroney.auth.action.ActionWithoutArg;
import com.github.qeroney.auth.model.User;
import com.github.qeroney.auth.service.user.UserService;
import io.github.qeroney.security.core.service.AuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class GetAuthenticatedUserAction implements ActionWithoutArg<User> {
    UserService userService;
    AuthService authService;

    @Override
    public User execute() {
        UUID userId = authService.getAuthorizedUserId();
        return userService.getById(userId);
    }
}
