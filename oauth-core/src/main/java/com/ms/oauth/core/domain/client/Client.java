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
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Client {

    private String clientId;

    private String clientName;

    private String clientSecret;

    private Set<String> redirectUris = new HashSet<>();

    private Set<GrantType> grantTypes = new HashSet<>();

    private Set<String> scopes = new HashSet<>();

    private ClientStatus status;

    private ClientSettings clientSettings;

    private TokenSettings tokenSettings;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt;

    public static Client create(String clientId, String clientName, String clientSecret, Set<String> redirectUris, Set<GrantType> grantTypes, Set<String> scopes, ClientSettings clientSettings, TokenSettings tokenSettings) {
        Client client = new Client();
        client.clientId = clientId;
        client.clientName = clientName;
        client.clientSecret = clientSecret;
        client.redirectUris = redirectUris;
        client.grantTypes = grantTypes;
        client.scopes = scopes;
        client.clientSettings = clientSettings;
        client.tokenSettings = tokenSettings;
        return client;
    }
}
