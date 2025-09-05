package com.github.qeroney.auth.config;

import com.github.qeroney.auth.config.properties.OAuth2TokenProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {

    @Value("${security.oauth2.client.access-token-validity-seconds:43200}")
    private int accessTokenValiditySeconds;

    @Value("${security.oauth2.client.refresh-token-validity-seconds:2592000}")
    private int refreshTokenValiditySeconds;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public TokenStore tokenStore(DataSource dataSource) {
        return new JdbcTokenStore(dataSource);
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(PasswordEncoder passwordEncoder,
                                                               UserDetailsService userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices(TokenStore tokenStore, OAuth2TokenProperties oAuth2TokenProperties) {
        DefaultTokenServices tokenServices = new DefaultTokenServices();

        tokenServices.setSupportRefreshToken(true);
        tokenServices.setTokenStore(tokenStore);
        tokenServices.setReuseRefreshToken(oAuth2TokenProperties.isReuseRefreshToken());
        tokenServices.setAccessTokenValiditySeconds(accessTokenValiditySeconds);
        tokenServices.setRefreshTokenValiditySeconds(refreshTokenValiditySeconds);

        return tokenServices;
    }
}
