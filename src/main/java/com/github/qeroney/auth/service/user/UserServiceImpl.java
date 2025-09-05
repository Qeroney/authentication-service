package com.github.qeroney.auth.service.user;

import com.github.qeroney.auth.exception.ConflictException;
import com.github.qeroney.auth.model.User;
import com.github.qeroney.auth.repository.UserRepository;
import com.github.qeroney.auth.service.user.argument.CreateUserArgument;
import com.github.qeroney.auth.service.user.argument.UpdateUserArgument;
import com.github.qeroney.auth.utils.AuthenticationUtils;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.transaction.annotation.Isolation;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserServiceImpl implements UserService {

    UserRepository repository;
    PasswordEncoder encoder;
    Cache<UUID, String> resetCodeCache = CacheBuilder.newBuilder()
                                                     .expireAfterWrite(10, TimeUnit.MINUTES)
                                                     .maximumSize(10000)
                                                     .build();

    @Override
    @Transactional
    public User create(@NonNull CreateUserArgument argument) {
        if (repository.existsByUsername(argument.getUsername())) throw new ConflictException("User.existsByUsername");

        return repository.save(User.builder()
                                   .username(argument.getUsername())
                                   .password(encoder.encode(argument.getPassword()))
                                   .authorities(argument.getAuthorities())
                                   .userProfile(argument.getUserProfile())
                                   .telegramChatId(argument.getTelegramChatId())
                                   .userType(argument.getUserType())
                                   .build());
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public User update(@NonNull UUID id, @NonNull UpdateUserArgument argument) {
        User user = getExisting(id);

        user.setUsername(argument.getUsername());
        user.setAuthorities(argument.getAuthorities());
        user.setUserProfile(argument.getUserProfile());
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
    public UUID createResetCode(@NonNull String username) {
        UUID code = UUID.randomUUID();

        resetCodeCache.put(code, username);

        return code;
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public User updatePasswordByCode(@NonNull String password, @NonNull UUID code) {
        String username = resetCodeCache.getIfPresent(code);

        if (username == null) throw new ConflictException("Code.isExpired");

        User user = getByUsername(username);
        user.setPassword(encoder.encode(password));
        resetCodeCache.invalidate(code);
        return user;
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

    @Override
    @Transactional(readOnly = true)
    public User getByUsername(@NonNull String username) {
        return repository.findByUsername(AuthenticationUtils.convertUsername(username))
                         .orElseThrow(() -> new ConflictException("User.notFound"));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getByTelegramChatId(@NonNull String telegramChatId) {
        return repository.findByTelegramChatId(telegramChatId);
    }

    @Override
    @Transactional(readOnly = true)
    public User getById(@NonNull UUID id) {
        return repository.findById(id)
                         .orElseThrow(() -> new ConflictException("User.notFound"));
    }
}
