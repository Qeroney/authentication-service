package com.github.qeroney.auth.service.user.argument;

import com.github.qeroney.auth.model.UserProfile;
import lombok.Builder;
import lombok.Value;

import java.util.Set;

@Value
@Builder
public class UpdateUserArgument {

    String username;

    Set<String> authorities;

    String telegramChatId;

    UserProfile userProfile;
}
