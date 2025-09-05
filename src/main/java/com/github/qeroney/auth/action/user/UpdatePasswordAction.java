package com.github.qeroney.auth.action.user;

import com.github.qeroney.auth.action.Action;
import com.github.qeroney.auth.action.user.argument.UpdatePasswordActionArgument;
import com.github.qeroney.auth.model.User;
import com.github.qeroney.auth.service.AuthenticationService;
import com.github.qeroney.auth.service.user.UserService;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UpdatePasswordAction implements Action<UpdatePasswordActionArgument, OAuth2AccessToken> {

    UserService userService;
    AuthenticationService authenticationService;

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public OAuth2AccessToken execute(@NonNull UpdatePasswordActionArgument argument) {
        User user = userService.updatePasswordByCode(argument.getPassword(), argument.getCode());

        return authenticationService.login(user.getUsername(), user.getPassword());
    }
}