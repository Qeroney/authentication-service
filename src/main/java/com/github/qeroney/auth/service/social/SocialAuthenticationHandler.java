package com.github.qeroney.auth.service.social;

import com.github.qeroney.auth.service.authentication.AuthenticationWithVerificationCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
public class SocialAuthenticationHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private final String webRedirectUrl;
    private final String mobileRedirectUrl;
    private final AuthenticationWithVerificationCodeService authenticationWithVerificationCodeService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        boolean isMobile = Boolean.TRUE.equals(request.getAttribute(SocialAuthFilter.PARAM_MOBILE));
        String redirectBase = isMobile ? mobileRedirectUrl : webRedirectUrl;
        UUID code = authenticationWithVerificationCodeService.createVerificationCode(authentication.getName());

        response.sendRedirect(redirectBase + "/" + code);
    }
}
