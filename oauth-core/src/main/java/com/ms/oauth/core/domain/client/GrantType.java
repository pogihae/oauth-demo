package com.ms.oauth.core.domain.client;

/**
 * OAuth 2.0 Grant Type을 나타내는 Enum
 */
public enum GrantType {
    AUTHORIZATION_CODE,
    IMPLICIT,
    PASSWORD,
    CLIENT_CREDENTIALS,
    REFRESH_TOKEN
}
