package com.ms.oauth.core.domain.account;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Account Aggregate Root (Pure Domain Model)
 * 독립적으로 관리되는 계정 도메인 모델
 * 인증을 위한 password, 2FA, 비밀번호 만료 등 지원
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {

    private String accountId;

    private String name;

    private String email;

    private String phoneNo;

    private String password;

    private AccountStatus status = AccountStatus.ACTIVE;

    /**
     * 마지막 2차 인증 검증 시각
     */
    private LocalDateTime lastTwoFactorVerifiedAt;

    /**
     * 비밀번호 마지막 변경 시각
     */
    private LocalDateTime passwordChangedAt;

    /**
     * 로그인 실패 횟수
     */
    private int failedLoginAttempts;

    /**
     * 계정 잠금 시각
     */
    private LocalDateTime lockedAt;

    /**
     * 마지막 로그인 시각
     */
    private LocalDateTime lastLoginAt;

    /**
     * 접근 가능한 Client ID 목록
     */
    private Set<String> accessibleClientIds;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt;

    public static Account create(String accountId, String name, String email, String phoneNo, String password, Set<String> accessibleClientIds) {
        Account account = new Account();
        account.accountId = accountId;
        account.name = name;
        account.email = email;
        account.phoneNo = phoneNo;
        account.password = password;
        account.accessibleClientIds = accessibleClientIds;
        account.passwordChangedAt = LocalDateTime.now();
        return account;
    }
}
