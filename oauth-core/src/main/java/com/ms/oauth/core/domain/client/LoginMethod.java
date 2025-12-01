package com.ms.oauth.core.domain.client;

/**
 * 로그인 방식
 */
public enum LoginMethod {
    /**
     * 이메일 + 비밀번호
     */
    EMAIL_PASSWORD,

    /**
     * 소셜 로그인 - Google
     */
    SOCIAL_GOOGLE,

    /**
     * 소셜 로그인 - Facebook
     */
    SOCIAL_FACEBOOK,

    /**
     * 소셜 로그인 - Naver
     */
    SOCIAL_NAVER,

    /**
     * 소셜 로그인 - Kakao
     */
    SOCIAL_KAKAO,

    /**
     * 생체 인증
     */
    BIOMETRIC,

    /**
     * 매직 링크 (이메일로 일회용 로그인 링크 발송)
     */
    MAGIC_LINK
}
