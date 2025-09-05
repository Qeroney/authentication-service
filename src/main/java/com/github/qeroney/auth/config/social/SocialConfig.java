package com.github.qeroney.auth.config.social;

import com.github.qeroney.auth.service.authentication.AuthenticationWithVerificationCodeService;
import com.github.qeroney.auth.service.social.AutoSignUpHandler;
import com.github.qeroney.auth.service.social.SocialAuthFilter;
import com.github.qeroney.auth.service.social.SocialAuthenticationHandler;
import com.github.qeroney.auth.service.social.SocialUserDetailsServiceImpl;
import com.github.qeroney.auth.service.user.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.security.SocialUserDetailsService;

@EnableSocial
@Configuration
public class SocialConfig {

    @Bean
    public ConnectionSignUp connectionSignUp(UserService  userService) {
        return new AutoSignUpHandler(userService);
    }

    @Bean
    public SocialAuthenticationHandler socialAuthenticationSuccessHandler(@Value("${social.webRedirectUrl}") String webRedirectUrl,
                                                                          @Value("${social.mobileRedirectUrl}") String mobileRedirectUrl,
                                                                          AuthenticationWithVerificationCodeService authenticationWithVerificationCodeService) {
        return new SocialAuthenticationHandler(webRedirectUrl,
                                                      mobileRedirectUrl,
                                                      authenticationWithVerificationCodeService);
    }

    @Bean
    public SocialUserDetailsService socialUserDetailsService(UserService userService) {
        return new SocialUserDetailsServiceImpl(userService);
    }

    @Bean
    public FilterRegistrationBean<SocialAuthFilter> socialAuthFilterReg() {
        FilterRegistrationBean<SocialAuthFilter> filterRegBean = new FilterRegistrationBean<>();

        filterRegBean.setFilter(new SocialAuthFilter());
        filterRegBean.addUrlPatterns("/auth/facebook",
                                     "/auth/google");
        filterRegBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        filterRegBean.setName("socialAuthFilter");
        filterRegBean.setEnabled(true);

        return filterRegBean;
    }
}
