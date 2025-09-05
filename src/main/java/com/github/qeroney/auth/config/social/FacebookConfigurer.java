package com.github.qeroney.auth.config.social;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.mem.InMemoryUsersConnectionRepository;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.security.AuthenticationNameUserIdSource;

@Configuration
@ConditionalOnBean(SocialConfig.class)
@RequiredArgsConstructor
public class FacebookConfigurer implements SocialConfigurer {
    private final ConnectionSignUp autoSignUpHandler;

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer cfConfig, Environment env) {
        cfConfig.addConnectionFactory(new FacebookConnectionFactory(env.getProperty("facebook.clientId",
                                                                                    System.getenv("facebook.clientId")),
                                                                    env.getProperty("facebook.clientSecret",
                                                                                    System.getenv("facebook.clientSecret"))));
    }

    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        InMemoryUsersConnectionRepository connectionRepository = new InMemoryUsersConnectionRepository(connectionFactoryLocator);
        connectionRepository.setConnectionSignUp(autoSignUpHandler);
        return connectionRepository;
    }
}
