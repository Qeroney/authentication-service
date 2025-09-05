package com.github.qeroney.auth.action.user.argument;

import com.github.qeroney.auth.model.UserProfile;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CompleteRegistrationTelegramUserActionArgument {

    String username;

    String telegramChatId;

    UserProfile userProfile;
}
