package com.ms.oauth.infrastructure.jpa.entity;

import com.ms.oauth.core.domain.account.AccountStatus;
import com.ms.oauth.infrastructure.jpa.converter.StringSetConverter;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "account", indexes = {
    @Index(name = "idx_account_email_name", columnList = "email, name"),
    @Index(name = "idx_account_phoneno_name", columnList = "phoneNo, name")
})
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountJpaEntity extends BaseEntity {

    @Id
    @Column(nullable = false, length = 50)
    private String accountId;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, unique = true, length = 100)
    private String phoneNo;

    @Column(nullable = false, length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AccountStatus status;

    private LocalDateTime lastTwoFactorVerifiedAt;

    @Column(nullable = false)
    private LocalDateTime passwordChangedAt;

    @Column(nullable = false)
    private int failedLoginAttempts;

    private LocalDateTime lockedAt;

    @Convert(converter = StringSetConverter.class)
    @Column(length = 1000)
    @Builder.Default
    private Set<String> accessibleClientIds = new HashSet<>();

    private LocalDateTime lastLoginAt;
}
