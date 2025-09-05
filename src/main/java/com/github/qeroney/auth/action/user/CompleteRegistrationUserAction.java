package com.github.qeroney.auth.action.user;

import com.github.qeroney.auth.action.Action;
import com.github.qeroney.auth.action.user.argument.CompleteRegistrationActionArgument;
import com.github.qeroney.auth.model.UserType;
import com.github.qeroney.auth.service.AuthenticationService;
import com.github.qeroney.auth.service.user.UserService;
import com.github.qeroney.auth.service.user.argument.CreateUserArgument;
import com.github.qeroney.auth.utils.AuthenticationUtils;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CompleteRegistrationUserAction implements Action<CompleteRegistrationActionArgument, OAuth2AccessToken> {

    UserService userService;
    AuthenticationService authenticationService;

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public OAuth2AccessToken execute(@NonNull CompleteRegistrationActionArgument argument) {
        String username = AuthenticationUtils.convertUsername(argument.getUsername());

        userService.create(CreateUserArgument.builder()
                                             .username(username)
                                             .password(argument.getPassword())
                                             .authorities(Set.of("USER"))
                                             .userProfile(argument.getUserProfile())
                                             .userType(UserType.SYSTEM)
                                             .build());

        return authenticationService.login(username, argument.getPassword());
    }
}
