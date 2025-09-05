package com.github.qeroney.auth.service.social;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.social.security.SocialUser;

import java.util.Collection;

@Getter
public class SocialUserDetailsImpl extends SocialUser {

    @Builder(builderMethodName = "getBuilder")
    public SocialUserDetailsImpl(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    @Override
    public String getUserId() {
        return getUsername();
    }
}
