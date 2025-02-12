package auth.api.user.external.auth.action.user;

import auth.api.user.external.auth.action.Action;
import auth.api.user.external.auth.action.user.argument.UpdatePasswordActionArgument;
import auth.api.user.external.auth.api.user.external.dto.AuthRequest;
import auth.api.user.external.auth.api.user.external.dto.AuthResponse;
import auth.api.user.external.auth.model.User;
import auth.api.user.external.auth.service.user.UserService;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UpdatePasswordAction implements Action<UpdatePasswordActionArgument, AuthResponse> {

    UserService userService;

    AuthenticationUserAction authenticationUserAction;

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public AuthResponse execute(@NonNull UpdatePasswordActionArgument argument) {
        User user = userService.updatePasswordByCode(argument.getPassword(), argument.getCode());

        return authenticationUserAction.execute(AuthRequest.builder()
                                                           .username(user.getUsername())
                                                           .password(argument.getPassword())
                                                           .build());
    }
}