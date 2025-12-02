package com.ms.oauth.infrastructure.redis.mapper;

import com.ms.oauth.core.domain.client.Client;
import com.ms.oauth.infrastructure.redis.entity.ClientRedisEntity;
import org.springframework.stereotype.Component;

import java.util.HashSet;

/**
 * Client Domain과 ClientRedisEntity 간의 변환을 담당하는 Mapper
 */
@Component
public class ClientRedisMapper {

    /**
     * Domain Model -> Redis Entity
     */
    public ClientRedisEntity toEntity(Client domain) {
        if (domain == null) {
            return null;
        }

        return ClientRedisEntity.builder()
                .clientId(domain.getClientId())
                .clientName(domain.getClientName())
                .clientSecret(domain.getClientSecret())
                .redirectUris(domain.getRedirectUris() != null
                        ? new HashSet<>(domain.getRedirectUris())
                        : new HashSet<>())
                .grantTypes(domain.getGrantTypes() != null
                        ? new HashSet<>(domain.getGrantTypes())
                        : new HashSet<>())
                .scopes(domain.getScopes() != null
                        ? new HashSet<>(domain.getScopes())
                        : new HashSet<>())
                .status(domain.getStatus())
                .clientSettings(domain.getClientSettings())
                .tokenSettings(domain.getTokenSettings())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }

    /**
     * Redis Entity -> Domain Model
     */
    public Client toDomain(ClientRedisEntity entity) {
        if (entity == null) {
            return null;
        }

        return Client.builder()
                .clientId(entity.getClientId())
                .clientName(entity.getClientName())
                .clientSecret(entity.getClientSecret())
                .redirectUris(entity.getRedirectUris() != null
                        ? new HashSet<>(entity.getRedirectUris())
                        : new HashSet<>())
                .grantTypes(entity.getGrantTypes() != null
                        ? new HashSet<>(entity.getGrantTypes())
                        : new HashSet<>())
                .scopes(entity.getScopes() != null
                        ? new HashSet<>(entity.getScopes())
                        : new HashSet<>())
                .status(entity.getStatus())
                .clientSettings(entity.getClientSettings())
                .tokenSettings(entity.getTokenSettings())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
