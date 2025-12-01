package com.ms.oauth.core.domain.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * OAuth Token Settings
 * JSON으로 저장되는 토큰 설정
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenSettings implements Serializable {

    /**
     * Access Token 유효 시간 (초)
     */
    @Builder.Default
    private int accessTokenTimeToLive = 3600; // 1 hour

    /**
     * Refresh Token 유효 시간 (초)
     */
    @Builder.Default
    private int refreshTokenTimeToLive = 86400; // 24 hours

    /**
     * Authorization Code 유효 시간 (초)
     */
    @Builder.Default
    private int authorizationCodeTimeToLive = 300; // 5 minutes

    /**
     * Refresh Token 재사용 허용 여부
     */
    @Builder.Default
    private boolean reuseRefreshTokens = true;

    /**
     * ID Token 서명 알고리즘
     */
    @Builder.Default
    private String idTokenSignatureAlgorithm = "RS256";

    /**
     * Access Token을 JWT 형식으로 발급할지 여부
     */
    @Builder.Default
    private boolean useJwtAccessToken = false;
}
