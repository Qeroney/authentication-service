package com.github.qeroney.auth.action.user;

import com.github.qeroney.auth.action.Action;
import com.github.qeroney.auth.action.user.argument.AuthenticationUserActionArgument;
import com.github.qeroney.auth.service.AuthenticationService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AuthenticationUserAction implements Action<AuthenticationUserActionArgument, OAuth2AccessToken> {

    private final AuthenticationManager authenticationManager;
    private final AuthenticationService authenticationService;

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public OAuth2AccessToken execute(@NonNull AuthenticationUserActionArgument argument) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(argument.getUsername(), argument.getPassword());
        authenticationManager.authenticate(authenticationToken);

        return authenticationService.login(argument.getUsername(), argument.getPassword());
    }
}
