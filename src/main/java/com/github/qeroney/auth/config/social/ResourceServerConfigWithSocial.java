package com.github.qeroney.auth.config.social;

import com.github.qeroney.auth.service.social.SocialAuthenticationHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.social.UserIdSource;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

@ConditionalOnBean(SocialConfig.class)
@EnableResourceServer
@Configuration
@RequiredArgsConstructor
public class ResourceServerConfigWithSocial extends ResourceServerConfigurerAdapter {

    private final SocialAuthenticationHandler socialAuthenticationHandler;
    private final UserIdSource userIdSource;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.apply(socialConfigurer().userIdSource(userIdSource));
    }

    @Bean
    public SpringSocialConfigurer socialConfigurer() {
        SpringSocialConfigurer configurer = new SpringSocialConfigurer();

        configurer.addObjectPostProcessor(new ObjectPostProcessor<SocialAuthenticationFilter>() {
            @Override
            public <FilterT extends SocialAuthenticationFilter> FilterT postProcess(FilterT socialAuthenticationFilter) {
                socialAuthenticationFilter.setAuthenticationSuccessHandler(socialAuthenticationHandler);
                return socialAuthenticationFilter;
            }
        });

        return configurer;
    }
}
