package com.github.qeroney.auth.service.social;

import com.github.qeroney.auth.model.User;
import com.github.qeroney.auth.model.UserType;
import com.github.qeroney.auth.service.user.UserService;
import com.github.qeroney.auth.service.user.argument.CreateUserArgument;
import com.google.common.base.Joiner;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UserProfile;

import java.util.Set;

@RequiredArgsConstructor
public class AutoSignUpHandler implements ConnectionSignUp {

    private final UserService userService;

    @Override
    public String execute(Connection<?> connection) {
        ConnectionData data = connection.createData();

        String username = getUsername(data);

        User user = userService.getByUsername(username);

        if (user == null) {
            UserProfile profile = connection.fetchUserProfile();

            userService.create(CreateUserArgument.builder()
                                                 .username(profile.getUsername())
                                                 .password(RandomStringUtils.randomAlphabetic(3))
                                                 .userProfile(com.github.qeroney.auth.model.UserProfile.builder()
                                                                                                       .firstName(profile.getFirstName())
                                                                                                       .middleName("N/A")
                                                                                                       .lastName(profile.getLastName())
                                                                                                       .build())
                                                 .authorities(Set.of("USER"))
                                                 .userType(getUserType(data.getProviderId()))
                                                 .build());
        }

        return user.getUsername();
    }

    private String getUsername(ConnectionData connectionData) {
        return Joiner.on("_")
                     .join(connectionData.getProviderId(),
                           connectionData.getProviderUserId());
    }

    private UserType getUserType(String provider) {
        return switch (provider) {
            case "facebook" -> UserType.FACEBOOK;
            case "google" -> UserType.GOOGLE;
            default -> UserType.SYSTEM;
        };
    }
}
