package com.github.qeroney.auth.service.user.argument;

import com.github.qeroney.auth.model.UserProfile;
import com.github.qeroney.auth.model.UserType;
import lombok.Builder;
import lombok.Value;

import java.util.Set;

@Value
@Builder
public class CreateUserArgument {

    String username;

    String password;

    Set<String> authorities;

    String telegramChatId;

    UserProfile userProfile;

    UserType userType;
}
