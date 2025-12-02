package com.ms.oauth.infrastructure.jpa.mapper;

import com.ms.oauth.core.domain.account.Account;
import com.ms.oauth.infrastructure.jpa.entity.AccountJpaEntity;
import org.springframework.stereotype.Component;

import java.util.HashSet;

/**
 * Account Domain과 AccountJpaEntity 간의 변환을 담당하는 Mapper
 */
@Component
public class AccountMapper {

    /**
     * Domain Model -> JPA Entity
     */
    public AccountJpaEntity toEntity(Account domain) {
        if (domain == null) {
            return null;
        }

        return AccountJpaEntity.builder()
                .accountId(domain.getAccountId())
                .name(domain.getName())
                .email(domain.getEmail())
                .phoneNo(domain.getPhoneNo())
                .password(domain.getPassword())
                .status(domain.getStatus())
                .lastTwoFactorVerifiedAt(domain.getLastTwoFactorVerifiedAt())
                .passwordChangedAt(domain.getPasswordChangedAt())
                .failedLoginAttempts(domain.getFailedLoginAttempts())
                .lockedAt(domain.getLockedAt())
                .accessibleClientIds(domain.getAccessibleClientIds() != null
                        ? new HashSet<>(domain.getAccessibleClientIds())
                        : new HashSet<>())
                .lastLoginAt(domain.getLastLoginAt())
                .build();
    }

    /**
     * JPA Entity -> Domain Model
     */
    public Account toDomain(AccountJpaEntity entity) {
        if (entity == null) {
            return null;
        }

        return new Account(
                entity.getAccountId(),
                entity.getName(),
                entity.getEmail(),
                entity.getPhoneNo(),
                entity.getPassword(),
                entity.getStatus(),
                entity.getLastTwoFactorVerifiedAt(),
                entity.getPasswordChangedAt(),
                entity.getFailedLoginAttempts(),
                entity.getLockedAt(),
                entity.getLastLoginAt(),
                entity.getAccessibleClientIds() != null
                        ? new HashSet<>(entity.getAccessibleClientIds())
                        : new HashSet<>(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
