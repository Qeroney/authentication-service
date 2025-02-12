package auth.service.user;

import auth.exception.ConflictException;
import auth.model.User;
import auth.repository.UserRepository;
import auth.service.user.argument.CreateUserArgument;
import auth.service.user.argument.UpdateUserArgument;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.transaction.annotation.Isolation;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserServiceImpl implements UserService {

    UserRepository repository;

    PasswordEncoder encoder;

    @Override
    @Transactional
    public User create(@NonNull CreateUserArgument argument) {
        return repository.save(User.builder()
                                   .username(argument.getUsername())
                                   .password(encoder.encode(argument.getPassword()))
                                   .contacts(argument.getContacts())
                                   .role(argument.getRole())
                                   .build());
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public User update(@NonNull UUID id, @NonNull UpdateUserArgument argument) {
        User user = getExisting(id);

        user.setUsername(argument.getUsername());
        user.setContacts(argument.getContacts());
        user.setRole(argument.getRole());
        return repository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User getExisting(@NonNull UUID id) {
        return repository.findById(id)
                         .orElseThrow(() -> new ConflictException("User.notFound"));
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public User resetPasswordByCode(@NonNull String username) {
        return repository.findByUsername(username)
                         .map(user -> {
                             user.setUpdateCode(UUID.randomUUID());
                             return user;
                         }).orElseThrow(() -> new ConflictException("User.notFound"));
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public User updatePasswordByCode(@NonNull String password, @NonNull UUID code) {
        return repository.findByUpdateCode(code)
                         .map(user -> {
                             user.setPassword(encoder.encode(password));
                             user.setUpdateCode(null);
                             return user;
                         }).orElseThrow(() -> new ConflictException("Code.notFound"));
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void updatePassword(@NonNull UUID id, @NonNull String password) {
        User user = getExisting(id);
        user.setPassword(encoder.encode(password));
        repository.save(user);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void enable(@NonNull UUID id) {
        User user = getExisting(id);
        user.setEnabled(true);
        repository.save(user);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void disable(@NonNull UUID id) {
        User user = getExisting(id);
        user.setEnabled(false);
        repository.save(user);
    }
}
