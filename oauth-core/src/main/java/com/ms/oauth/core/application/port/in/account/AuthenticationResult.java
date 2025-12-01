package com.ms.oauth.core.application.port.in.account;

import com.ms.oauth.core.domain.account.Account;
import lombok.Builder;
import lombok.Getter;

/**
 * 인증 결과
 */
@Getter
@Builder
public class AuthenticationResult {

    /**
     * 인증된 Account
     */
    private final Account account;

    /**
     * 인증 성공 여부
     */
    private final boolean success;

    /**
     * 2차 인증 필요 여부
     */
    private final boolean requiresTwoFactor;

    /**
     * 비밀번호 만료 여부
     */
    private final boolean passwordExpired;

    /**
     * 계정 잠금 여부
     */
    private final boolean accountLocked;

    /**
     * 실패 메시지
     */
    private final String failureReason;

    public static AuthenticationResult success(Account account, boolean requiresTwoFactor, boolean passwordExpired) {
        return AuthenticationResult.builder()
            .account(account)
            .success(true)
            .requiresTwoFactor(requiresTwoFactor)
            .passwordExpired(passwordExpired)
            .accountLocked(false)
            .build();
    }

    public static AuthenticationResult failure(String reason) {
        return AuthenticationResult.builder()
            .success(false)
            .failureReason(reason)
            .build();
    }

    public static AuthenticationResult accountLocked(Account account) {
        return AuthenticationResult.builder()
            .account(account)
            .success(false)
            .accountLocked(true)
            .failureReason("Account is locked")
            .build();
    }
}
