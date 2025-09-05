package com.github.qeroney.auth.service.details;

import com.github.qeroney.auth.exception.ConflictException;
import com.github.qeroney.auth.model.User;
import com.github.qeroney.auth.repository.UserRepository;
import com.github.qeroney.auth.utils.AuthenticationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import static com.github.qeroney.auth.exception.ConflictException.ACCESS_DENIED;
import static com.github.qeroney.auth.exception.ConflictException.BAD_CREDENTIALS;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String convertedUsername = AuthenticationUtils.convertUsername(username);
        User user = repository.findByUsername(convertedUsername)
                              .orElseThrow(() -> new ConflictException(BAD_CREDENTIALS));

        if (!user.isEnabled()) throw new ConflictException(ACCESS_DENIED);

        return getUserDetails(user);
    }

    private AppUserDetails getUserDetails(User user) {
        return AppUserDetails.builder()
                             .id(user.getId())
                             .authorities(user.getAuthorities().stream()
                                               .map(SimpleGrantedAuthority::new)
                                               .collect(Collectors.toSet()))
                             .username(user.getUsername())
                             .password(user.getPassword())
                             .firstName(user.getUserProfile().getFirstName())
                             .middleName(user.getUserProfile().getMiddleName())
                             .lastName(user.getUserProfile().getLastName())
                             .enabled(user.isEnabled())
                             .userType(user.getUserType())
                             .accountNonExpired(true)
                             .accountNonLocked(true)
                             .credentialsNonExpired(true)
                             .build();
    }
}
