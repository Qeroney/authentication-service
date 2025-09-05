package com.github.qeroney.auth.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;

import static com.github.qeroney.auth.exception.ConflictException.BAD_CREDENTIALS;

public class CustomWebExceptionTranslator extends DefaultWebResponseExceptionTranslator {

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
        ResponseEntity<OAuth2Exception> result = super.translate(e);
        if (e instanceof InvalidGrantException) {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Cache-Control", "no-store");
            headers.set("Pragma", "no-cache");
            return new ResponseEntity<>(new InvalidGrantException(BAD_CREDENTIALS)
                    , headers, HttpStatus.FORBIDDEN);

        } else if (e instanceof InternalAuthenticationServiceException) {
            return new ResponseEntity<>(new OAuth2Exception(e.getMessage(), e), HttpStatus.FORBIDDEN);
        }
        return result;
    }
}
