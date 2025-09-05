package com.github.qeroney.auth.config.properties;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@NoArgsConstructor
@ConfigurationProperties(prefix = "oauth2.client")
public class OAuth2ClientProperties {

    private OAuth2Client web = OAuth2Client.builder()
                                           .clientId("web")
                                           .scope("web")
                                           .build();

    private OAuth2Client android = OAuth2Client.builder()
                                               .clientId("android")
                                               .scope("android")
                                               .build();

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = PRIVATE)
    public static class OAuth2Client {
        /** идентификатор клиента */
        String clientId;

        /** пароль клиента */
        String clientSecret;

        /** область доступа */
        String scope;
    }
}
