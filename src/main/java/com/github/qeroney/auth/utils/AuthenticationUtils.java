package com.github.qeroney.auth.utils;

import com.github.qeroney.auth.exception.ConflictException;
import lombok.experimental.UtilityClass;

import java.util.regex.Pattern;

@UtilityClass
public class AuthenticationUtils {

    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^(\\+7|8)?\\s?\\(?\\d{3}\\)?[\\s\\-]?\\d{3}[\\s\\-]?\\d{2}[\\s\\-]?\\d{2}$");

    public static String convertToPhone(String username) {
        if (!isLoginByPhone(username)) return username;

        if (!username.startsWith("+")) username = username.length() == 10 ?
                                                  "+7" + username :
                                                  "+7" + username.substring(1);
        return username.replaceAll("\\(?\\)?-?", "");
    }

    public static boolean isLoginByPhone(String username) {
        if (username == null) throw new ConflictException("System.error.username.isNull");
        return PHONE_PATTERN.matcher(username).matches();
    }

    public static String convertUsername(String username) {
        if (username == null) throw new ConflictException("System.error.username.isNull");
        return isLoginByPhone(username)
               ? convertToPhone(username)
               : username.toLowerCase().trim();
    }
}
