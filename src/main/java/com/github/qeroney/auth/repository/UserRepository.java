package com.github.qeroney.auth.repository;

import com.github.qeroney.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUsername(String username);

    Optional<User> findByTelegramChatId(String telegramChatId);

    boolean existsByUsername(String username);
}
