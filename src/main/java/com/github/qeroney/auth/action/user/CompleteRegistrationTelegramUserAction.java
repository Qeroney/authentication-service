package com.github.qeroney.auth.action.user;

import com.github.qeroney.auth.action.Action;
import com.github.qeroney.auth.action.user.argument.CompleteRegistrationTelegramUserActionArgument;
import com.github.qeroney.auth.model.User;
import com.github.qeroney.auth.model.UserType;
import com.github.qeroney.auth.service.user.UserService;
import com.github.qeroney.auth.service.user.argument.CreateUserArgument;
import com.github.qeroney.auth.utils.AuthenticationUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CompleteRegistrationTelegramUserAction implements Action<CompleteRegistrationTelegramUserActionArgument, UUID> {

    private final UserService userService;

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public UUID execute(@NonNull CompleteRegistrationTelegramUserActionArgument argument) {
        return userService.getByTelegramChatId(argument.getTelegramChatId())
                          .map(User::getId)
                          .orElseGet(() -> {
                              User user = userService.create(CreateUserArgument.builder()
                                                                               .username(AuthenticationUtils.convertUsername(argument.getUsername()))
                                                                               .password("N/A")
                                                                               .authorities(Set.of("BOT", "USER"))
                                                                               .telegramChatId(argument.getTelegramChatId())
                                                                               .userProfile(argument.getUserProfile())
                                                                               .userType(UserType.TELEGRAM)
                                                                               .build());
                              return user.getId();
                          });
    }
}
