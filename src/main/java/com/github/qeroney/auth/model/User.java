package com.github.qeroney.auth.model;

import lombok.*;
import javax.persistence.*;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
@Table(name = "user_account")
public class User {
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    UUID id;

    @Column(name = "username", nullable = false)
    String username;

    @Column(name = "password", nullable = false)
    String password;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "authorities",
                     joinColumns = @JoinColumn(name = "user_id"),
                     uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "authorities"})})
    Set<String> authorities;

    @Builder.Default
    @Column(name = "enabled", nullable = false, columnDefinition = "boolean default true")
    boolean enabled = true;

    @Column(name = "chat_id", nullable = true)
    String telegramChatId;

    @Embedded
    UserProfile userProfile;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    UserType userType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User that)) return false;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
