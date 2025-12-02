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

        return new Client(
                entity.getClientId(),
                entity.getClientName(),
                entity.getClientSecret(),
                entity.getRedirectUris() != null
                        ? new HashSet<>(entity.getRedirectUris())
                        : new HashSet<>(),
                entity.getGrantTypes() != null
                        ? new HashSet<>(entity.getGrantTypes())
                        : new HashSet<>(),
                entity.getScopes() != null
                        ? new HashSet<>(entity.getScopes())
                        : new HashSet<>(),
                entity.getStatus(),
                entity.getClientSettings(),
                entity.getTokenSettings(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
