package com.github.qeroney.auth.config.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@Component
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
@ConfigurationProperties(prefix = "oauth2.token")
public class OAuth2TokenProperties {

    private boolean reuseRefreshToken = false;
}
