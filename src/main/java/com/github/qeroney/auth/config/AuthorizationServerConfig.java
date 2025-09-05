package com.github.qeroney.auth.config;

import com.github.qeroney.auth.config.properties.OAuth2ClientProperties;
import com.github.qeroney.auth.config.properties.OAuth2TokenProperties;
import com.github.qeroney.auth.exception.CustomWebExceptionTranslator;
import com.github.qeroney.auth.service.details.CustomUserDetailsService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(OAuth2ClientProperties.class)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    CustomUserDetailsService userDetailsService;
    AuthenticationManager authenticationManager;
    PasswordEncoder passwordEncoder;
    TokenStore tokenStore;
    DefaultTokenServices defaultTokenServices;
    OAuth2ClientProperties oAuth2ClientProperties;
    OAuth2TokenProperties oAuth2TokenProperties;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
               // Web
               .withClient(oAuth2ClientProperties.getWeb().getClientId())
               .secret(passwordEncoder.encode(oAuth2ClientProperties.getWeb().getClientSecret()))
               .scopes(oAuth2ClientProperties.getWeb().getScope())
               .authorizedGrantTypes("password", "refresh_token", "client_credentials")

               // Android
               .and()
               .withClient(oAuth2ClientProperties.getAndroid().getClientId())
               .secret(passwordEncoder.encode(oAuth2ClientProperties.getAndroid().getClientSecret()))
               .scopes(oAuth2ClientProperties.getAndroid().getScope())
               .authorizedGrantTypes("password", "refresh_token", "client_credentials");
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        oauthServer
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");

    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.userDetailsService(userDetailsService)
                 .allowedTokenEndpointRequestMethods(HttpMethod.POST, HttpMethod.GET)
                 .tokenStore(tokenStore)
                 .tokenServices(defaultTokenServices)
                 .authenticationManager(authenticationManager)
                 .reuseRefreshTokens(oAuth2TokenProperties.isReuseRefreshToken())
                 .exceptionTranslator(new CustomWebExceptionTranslator());
    }
}