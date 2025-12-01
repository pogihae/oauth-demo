package com.ms.oauth.core.domain.client;

/**
 * 2차 인증 수단
 */
public enum TwoFactorMethod {
    /**
     * SMS 인증
     */
    SMS,

    /**
     * 이메일 인증
     */
    EMAIL,

    /**
     * TOTP (Time-based One-Time Password) - Google Authenticator 등
     */
    TOTP,

    /**
     * 생체 인증
     */
    BIOMETRIC,

    /**
     * 백업 코드
     */
    BACKUP_CODE
}
