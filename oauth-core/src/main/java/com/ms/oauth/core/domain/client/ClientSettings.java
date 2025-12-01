package com.ms.oauth.core.domain.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * OAuth Client Settings
 * JSON으로 저장되는 클라이언트 설정
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientSettings implements Serializable {

    // ========== OAuth 설정 ==========

    /**
     * PKCE 요구 여부
     */
    @Builder.Default
    private boolean requireProofKey = false;

    /**
     * Authorization Code 발급 시 Consent 화면 표시 여부
     */
    @Builder.Default
    private boolean requireAuthorizationConsent = true;

    /**
     * JWT를 사용한 Client Authentication 허용 여부
     */
    @Builder.Default
    private boolean requireJwtClientAuthentication = false;

    /**
     * Client가 등록한 Redirect URI를 엄격하게 검증할지 여부
     */
    @Builder.Default
    private boolean strictRedirectUriMatching = true;

    // ========== 로그인 및 인증 설정 ==========

    /**
     * 2차 인증 요구 여부
     */
    @Builder.Default
    private boolean requireTwoFactor = false;

    /**
     * 2차 인증 패스 기간 (분)
     * 2차 인증 성공 후 이 시간 동안은 2차 인증을 생략할 수 있음
     * 0이면 항상 2차 인증 필요
     */
    @Builder.Default
    private int twoFactorPassPeriodMinutes = 0;

    /**
     * 비밀번호 만료 기간 (일)
     * 0이면 만료 없음
     */
    @Builder.Default
    private int passwordExpiryDays = 90;

    /**
     * 최대 로그인 실패 횟수
     * 이 횟수를 초과하면 계정 잠금
     */
    @Builder.Default
    private int maxFailedLoginAttempts = 5;

    /**
     * 지원하는 2차 인증 수단 목록
     */
    @Builder.Default
    private Set<TwoFactorMethod> supportedTwoFactorMethods = new HashSet<>(Set.of(
        TwoFactorMethod.SMS,
        TwoFactorMethod.EMAIL,
        TwoFactorMethod.TOTP
    ));

    /**
     * 지원하는 로그인 방식 목록
     */
    @Builder.Default
    private Set<LoginMethod> supportedLoginMethods = new HashSet<>(Set.of(
        LoginMethod.EMAIL_PASSWORD
    ));

    /**
     * 계정 잠금 자동 해제 시간 (분)
     * 0이면 수동으로만 해제 가능
     */
    @Builder.Default
    private int autoUnlockMinutes = 30;
}
