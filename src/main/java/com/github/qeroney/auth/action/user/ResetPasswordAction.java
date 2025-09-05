package com.github.qeroney.auth.action.user;

import com.github.qeroney.auth.action.VoidAction;
import com.github.qeroney.auth.exception.ConflictException;
import com.github.qeroney.auth.service.user.UserService;
import com.github.qeroney.auth.utils.AuthenticationUtils;
import io.qeroney.notification.service.NotificationService;
import io.qeroney.notification.service.event.ContentType;
import io.qeroney.notification.service.event.TransportType;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ResetPasswordAction implements VoidAction<String> {

    UserService userService;
    NotificationService notificationService;

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void execute(@NonNull String username) {
        String converted = AuthenticationUtils.convertUsername(username);
        if (AuthenticationUtils.isLoginByPhone(converted)) throw new ConflictException("System.error.phone.notSupport");
        UUID code = userService.createResetCode(converted);

        String emailTemplate = loadTemplate();
        String htmlContent = String.format(emailTemplate, code);

        notificationService.toAddress()
                           .title("Ваш код для сброса пароля")
                           .message(htmlContent)
                           .recipient(converted)
                           .contentType(ContentType.HTML)
                           .transportType(TransportType.EMAIL)
                           .send();
    }

    private String loadTemplate() {
        try {
            ClassPathResource resource = new ClassPathResource("templates/reset-password-email.html");
            InputStreamReader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new ConflictException("System.error.template");
        }
    }
}
