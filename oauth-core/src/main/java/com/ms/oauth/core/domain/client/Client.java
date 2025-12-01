package com.ms.oauth.core.domain.client;

import com.ms.oauth.core.domain.common.validation.ValidClientSecret;
import com.ms.oauth.core.domain.common.validation.ValidName;
import com.ms.oauth.core.domain.customer.CustomerId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * OAuth Client Aggregate Root
 * Customer와 ID 참조 관계를 가지며, OAuth 클라이언트를 나타냄
 * 비즈니스 규칙: Customer는 최대 3개의 Client를 가질 수 있음 (Application Layer에서 검증)
 */
@Entity
@Table(name = "oauth_clients", indexes = {
    @Index(name = "idx_client_customer_id", columnList = "customer_id"),
    @Index(name = "idx_client_name", columnList = "client_name")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Client {

    @EmbeddedId
    private ClientId id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "customer_id", nullable = false))
    private CustomerId customerId;

    @ValidName
    @Column(nullable = false, unique = true, length = 100, name = "client_name")
    private String clientName;

    @ValidClientSecret
    @Column(nullable = false, length = 255, name = "client_secret")
    private String clientSecret;

    @NotEmpty(message = "At least one redirect URI is required")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "client_redirect_uris", joinColumns = @JoinColumn(name = "client_id"))
    @Column(name = "redirect_uri", nullable = false, length = 500)
    private Set<String> redirectUris = new HashSet<>();

    @NotEmpty(message = "At least one grant type is required")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "client_grant_types", joinColumns = @JoinColumn(name = "client_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "grant_type", nullable = false, length = 50)
    private Set<GrantType> grantTypes = new HashSet<>();

    @NotEmpty(message = "At least one scope is required")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "client_scopes", joinColumns = @JoinColumn(name = "client_id"))
    @Column(name = "scope", nullable = false, length = 50)
    private Set<String> scopes = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ClientStatus status;

    /**
     * Client 설정 (JSON 저장)
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "client_settings", columnDefinition = "json")
    private ClientSettings clientSettings;

    /**
     * Token 설정 (JSON 저장)
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "token_settings", columnDefinition = "json")
    private TokenSettings tokenSettings;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Client(
        ClientId id,
        CustomerId customerId,
        String clientName,
        String clientSecret,
        Set<String> redirectUris,
        Set<GrantType> grantTypes,
        Set<String> scopes,
        ClientSettings clientSettings,
        TokenSettings tokenSettings
    ) {
        this.id = id;
        this.customerId = customerId;
        this.clientName = clientName;
        this.clientSecret = clientSecret;
        this.redirectUris = new HashSet<>(redirectUris);
        this.grantTypes = new HashSet<>(grantTypes);
        this.scopes = new HashSet<>(scopes);
        this.status = ClientStatus.ACTIVE;
        this.clientSettings = clientSettings != null ? clientSettings : ClientSettings.builder().build();
        this.tokenSettings = tokenSettings != null ? tokenSettings : TokenSettings.builder().build();
        this.createdAt = LocalDateTime.now();
    }

    public static Client create(
        CustomerId customerId,
        String clientName,
        String clientSecret,
        Set<String> redirectUris,
        Set<GrantType> grantTypes,
        Set<String> scopes
    ) {
        return new Client(
            ClientId.generate(),
            customerId,
            clientName,
            clientSecret,
            redirectUris,
            grantTypes,
            scopes,
            ClientSettings.builder().build(),
            TokenSettings.builder().build()
        );
    }

    public static Client create(
        CustomerId customerId,
        String clientName,
        String clientSecret,
        Set<String> redirectUris,
        Set<GrantType> grantTypes,
        Set<String> scopes,
        ClientSettings clientSettings,
        TokenSettings tokenSettings
    ) {
        return new Client(
            ClientId.generate(),
            customerId,
            clientName,
            clientSecret,
            redirectUris,
            grantTypes,
            scopes,
            clientSettings,
            tokenSettings
        );
    }

    /**
     * Client 정보 업데이트
     */
    public void updateClientInfo(
        @ValidName String clientName,
        @NotEmpty(message = "At least one redirect URI is required") Set<String> redirectUris,
        @NotEmpty(message = "At least one grant type is required") Set<GrantType> grantTypes,
        @NotEmpty(message = "At least one scope is required") Set<String> scopes
    ) {
        this.clientName = clientName;
        this.redirectUris = new HashSet<>(redirectUris);
        this.grantTypes = new HashSet<>(grantTypes);
        this.scopes = new HashSet<>(scopes);
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Client Secret 재발급
     */
    public void regenerateClientSecret(@ValidClientSecret String newClientSecret) {
        this.clientSecret = newClientSecret;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Client Settings 업데이트
     */
    public void updateClientSettings(ClientSettings clientSettings) {
        if (clientSettings == null) {
            throw new IllegalArgumentException("Client settings cannot be null");
        }
        this.clientSettings = clientSettings;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Token Settings 업데이트
     */
    public void updateTokenSettings(TokenSettings tokenSettings) {
        if (tokenSettings == null) {
            throw new IllegalArgumentException("Token settings cannot be null");
        }
        this.tokenSettings = tokenSettings;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Client 활성화
     */
    public void activate() {
        if (this.status == ClientStatus.ACTIVE) {
            throw new IllegalStateException("Client is already active");
        }
        this.status = ClientStatus.ACTIVE;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Client 비활성화
     */
    public void deactivate() {
        if (this.status == ClientStatus.INACTIVE) {
            throw new IllegalStateException("Client is already inactive");
        }
        this.status = ClientStatus.INACTIVE;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isActive() {
        return this.status == ClientStatus.ACTIVE;
    }

    /**
     * Redirect URI 검증
     */
    public boolean isValidRedirectUri(String redirectUri) {
        if (this.clientSettings.isStrictRedirectUriMatching()) {
            return this.redirectUris.contains(redirectUri);
        }
        // Non-strict 모드일 경우 prefix 매칭
        return this.redirectUris.stream()
            .anyMatch(uri -> redirectUri.startsWith(uri));
    }

    /**
     * Grant Type 지원 여부 확인
     */
    public boolean supportsGrantType(GrantType grantType) {
        return this.grantTypes.contains(grantType);
    }

    /**
     * Scope 검증
     */
    public boolean hasScope(String scope) {
        return this.scopes.contains(scope);
    }

    /**
     * Access Token 유효시간 조회 (TokenSettings 기반)
     */
    public int getAccessTokenTimeToLive() {
        return this.tokenSettings.getAccessTokenTimeToLive();
    }

    /**
     * Refresh Token 유효시간 조회 (TokenSettings 기반)
     */
    public int getRefreshTokenTimeToLive() {
        return this.tokenSettings.getRefreshTokenTimeToLive();
    }
}
