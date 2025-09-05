package com.github.qeroney.auth.action.user.argument;

import com.github.qeroney.auth.model.UserProfile;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CompleteRegistrationActionArgument {

    String username;

    String password;

    UserProfile userProfile;
}
