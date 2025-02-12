package auth.action.user;

import auth.action.VoidAction;
import auth.email.EmailSender;
import auth.email.dto.EmailDto;
import auth.model.User;
import auth.service.user.UserService;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ResetPasswordAction implements VoidAction<String> {

    EmailSender emailSender;

    UserService userService;

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void execute(@NonNull String username) {
        User user = userService.resetPasswordByCode(username);

        emailSender.sendSimpleMail(EmailDto.builder()
                                           .recipient(user.getContacts().getEmail())
                                           .body("Your reset code is: " + user.getUpdateCode())
                                           .subject("Password Reset Code")
                                           .build());
    }
}
