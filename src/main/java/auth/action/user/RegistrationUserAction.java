package auth.action.user;

import auth.action.Action;
import auth.api.user.external.dto.AuthRequest;
import auth.api.user.external.dto.AuthResponse;
import auth.api.user.external.dto.RegisterRequest;
import auth.service.user.UserService;
import auth.service.user.argument.CreateUserArgument;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.vadim.user.Role;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RegistrationUserAction implements Action<RegisterRequest, AuthResponse> {

    AuthenticationUserAction authUserAction;

    UserService userService;

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public AuthResponse execute(@NonNull RegisterRequest request) {
        userService.create(CreateUserArgument.builder()
                                             .username(request.getUsername())
                                             .password(request.getPassword())
                                             .contacts(request.getContacts())
                                             .role(Role.USER)
                                             .build());

        return authUserAction.execute(AuthRequest.builder()
                                                 .username(request.getUsername())
                                                 .password(request.getPassword())
                                                 .build());
    }
}
