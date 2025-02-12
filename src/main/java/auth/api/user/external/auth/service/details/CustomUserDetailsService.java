package auth.api.user.external.auth.service.details;

import auth.api.user.external.auth.exception.ConflictException;
import auth.api.user.external.auth.model.User;
import auth.api.user.external.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.vadim.user.CustomUserDetails;

import static auth.exception.ConflictException.ACCESS_DENIED;
import static auth.exception.ConflictException.BAD_CREDENTIALS;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username)
                              .orElseThrow(() -> new ConflictException(BAD_CREDENTIALS));

        if (!user.isEnabled()) throw new ConflictException(ACCESS_DENIED);

        return getUserDetails(user);
    }

    private CustomUserDetails getUserDetails(User user) {
        return CustomUserDetails.builder()
                                .id(user.getId())
                                .role(user.getRole())
                                .username(user.getUsername())
                                .password(user.getPassword())
                                .isEnabled(user.isEnabled())
                                .isAccountNonExpired(true)
                                .isAccountNonLocked(true)
                                .isCredentialsNonExpired(true)
                                .build();
    }
}
