package auth.api.user.external.auth.action.user;

import auth.api.user.external.auth.action.Action;
import auth.api.user.external.auth.api.user.external.dto.AuthRequest;
import auth.api.user.external.auth.api.user.external.dto.AuthResponse;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.vadim.security.JwtService;
import ru.vadim.user.CustomUserDetails;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AuthenticationUserAction implements Action<AuthRequest, AuthResponse> {

    JwtService jwtService;

    AuthenticationManager authenticationManager;

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public AuthResponse execute(@NonNull AuthRequest request) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        CustomUserDetails userDetails = (CustomUserDetails) authenticate.getPrincipal();

        String accessToken = jwtService.generateAccessToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        return AuthResponse.builder()
                           .accessToken(accessToken)
                           .refreshToken(refreshToken)
                           .build();
    }
}
