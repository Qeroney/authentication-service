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
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.security.AuthenticationNameUserIdSource;

@Configuration
@ConditionalOnBean(SocialConfig.class)
@RequiredArgsConstructor
public class GoogleConfigurer implements SocialConfigurer {

    private final ConnectionSignUp autoSignUpHandler;

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer cfConfig, Environment env) {
        cfConfig.addConnectionFactory(new GoogleConnectionFactory(env.getProperty("google.clientId",
                                                                                  System.getenv("google.clientId")),
                                                                  env.getProperty("google.clientSecret",
                                                                                  System.getenv("google.clientSecret"))));
    }

    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator factoryLocator) {
        InMemoryUsersConnectionRepository repo = new InMemoryUsersConnectionRepository(factoryLocator);
        repo.setConnectionSignUp(autoSignUpHandler);
        return repo;
    }
}
