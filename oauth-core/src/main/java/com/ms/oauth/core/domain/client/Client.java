package com.ms.oauth.core.domain.client;

import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * OAuth Client Aggregate Root (Pure Domain Model)
 * OAuth 클라이언트를 나타내는 순수 도메인 모델
 */
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Client {

    private String clientId;
    private String clientName;
    private String clientSecret;

    @Builder.Default
    private Set<String> redirectUris = new HashSet<>();

    @Builder.Default
    private Set<String> grantTypes = new HashSet<>();

    @Builder.Default
    private Set<String> scopes = new HashSet<>();

    private ClientStatus status;

    private ClientSettings clientSettings;

    private TokenSettings tokenSettings;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
