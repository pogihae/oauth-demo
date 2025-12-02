package com.ms.oauth.infrastructure.jpa.mapper;

import com.ms.oauth.core.domain.client.Client;
import com.ms.oauth.infrastructure.jpa.entity.ClientJpaEntity;
import org.springframework.stereotype.Component;

import java.util.HashSet;

/**
 * Client Domain과 ClientJpaEntity 간의 변환을 담당하는 Mapper
 */
@Component
public class ClientMapper {

    /**
     * Domain Model -> JPA Entity
     */
    public ClientJpaEntity toEntity(Client domain) {
        if (domain == null) {
            return null;
        }

        return ClientJpaEntity.builder()
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
                .build();
    }

    /**
     * JPA Entity -> Domain Model
     */
    public Client toDomain(ClientJpaEntity entity) {
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
