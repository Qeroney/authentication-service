package com.github.qeroney.auth.service.authentication;

import com.github.qeroney.auth.exception.ConflictException;
import com.github.qeroney.auth.service.AuthenticationService;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthenticationWithVerificationServiceImpl implements AuthenticationWithVerificationCodeService {
    private final AuthenticationService authenticationService;
    private final Cache<UUID, String> cache = CacheBuilder.newBuilder()
                                                          .expireAfterWrite(10, TimeUnit.MINUTES)
                                                          .maximumSize(10000)
                                                          .build();

    @Override
    public UUID createVerificationCode(String username) {
        UUID code = UUID.randomUUID();

        cache.put(code, username);

        return code;
    }

    @Override
    public OAuth2AccessToken loginByVerificationCode(UUID code) {
        String username = cache.getIfPresent(code);

        if (username == null) throw new ConflictException("Code.isExpired");

        cache.invalidate(code);

        return authenticationService.login(username, "N/A");
    }

    @Override
    public String getByUsernameByCode(UUID code) {
        String username = cache.getIfPresent(code);

        cache.invalidate(code);

        return username;
    }
}
