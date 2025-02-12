package auth.api.user.external.auth.email;

import auth.api.user.external.auth.email.dto.EmailDto;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailSender {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public void sendSimpleMail(@NonNull EmailDto dto) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(sender);
        mailMessage.setTo(dto.getRecipient());
        mailMessage.setText(dto.getBody());
        mailMessage.setSubject(dto.getSubject());

        javaMailSender.send(mailMessage);
    }
}
