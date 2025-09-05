package com.github.qeroney.auth.service.social;

import com.github.qeroney.auth.model.User;
import com.github.qeroney.auth.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;

import java.util.stream.Collectors;

@RequiredArgsConstructor
public class SocialUserDetailsServiceImpl implements SocialUserDetailsService {

    private final UserService userService;

    @Override
    public SocialUserDetails loadUserByUserId(String username) throws UsernameNotFoundException {
        User user = userService.getByUsername(username);

        if (user == null) throw new UsernameNotFoundException("User.notFound");

        return SocialUserDetailsImpl.getBuilder()
                                    .username(username)
                                    .password(user.getPassword())
                                    .authorities(user.getAuthorities().stream()
                                                     .map(SimpleGrantedAuthority::new)
                                                     .collect(Collectors.toSet()))
                                    .build();
    }
}

