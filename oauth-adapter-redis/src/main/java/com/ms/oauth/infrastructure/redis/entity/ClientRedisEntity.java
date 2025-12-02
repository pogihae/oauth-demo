package com.ms.oauth.infrastructure.redis.entity;

import com.ms.oauth.core.domain.client.ClientSettings;
import com.ms.oauth.core.domain.client.ClientStatus;
import com.ms.oauth.core.domain.client.TokenSettings;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Client Redis Entity
 * Redis에 저장되는 Client 캐시 엔티티
 * TTL: 24시간
 */
@RedisHash("oauth:client")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientRedisEntity {

    @Id
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

    /**
     * TTL: 24시간 (86400초)
     */
    @TimeToLive(unit = TimeUnit.SECONDS)
    private Long ttl = 86400L;
}
