package com.github.qeroney.auth.action.user.argument;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AuthenticationUserActionArgument {
    String username;

    String password;
}
