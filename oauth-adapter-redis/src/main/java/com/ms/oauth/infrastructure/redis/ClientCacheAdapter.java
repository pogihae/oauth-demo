package com.ms.oauth.infrastructure.redis;

import com.ms.oauth.core.domain.client.Client;
import com.ms.oauth.infrastructure.redis.entity.ClientRedisEntity;
import com.ms.oauth.infrastructure.redis.mapper.ClientRedisMapper;
import com.ms.oauth.infrastructure.redis.port.ClientCachePort;
import com.ms.oauth.infrastructure.redis.repository.ClientRedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Client Cache Adapter (Infrastructure Layer)
 * Redis를 사용한 ClientCachePort 구현체
 * Hexagonal Architecture의 Outbound Adapter
 * Spring Data Redis Repository 패턴 사용
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ClientCacheAdapter implements ClientCachePort {

    private final ClientRedisRepository repository;
    private final ClientRedisMapper mapper;

    @Override
    public Optional<Client> findById(String clientId) {
        try {
            Optional<ClientRedisEntity> entity = repository.findById(clientId);
            if (entity.isPresent()) {
                log.debug("Cache hit for client: {}", clientId);
                return entity.map(mapper::toDomain);
            } else {
                log.debug("Cache miss for client: {}", clientId);
                return Optional.empty();
            }
        } catch (Exception e) {
            log.error("Failed to get client from cache: {}", clientId, e);
            return Optional.empty();
        }
    }

    @Override
    public void save(Client client) {
        try {
            ClientRedisEntity entity = mapper.toEntity(client);
            repository.save(entity);
            log.debug("Client saved to cache: {}", client.getClientId());
        } catch (Exception e) {
            log.error("Failed to save client to cache: {}", client.getClientId(), e);
        }
    }

    @Override
    public void update(Client client) {
        try {
            ClientRedisEntity entity = mapper.toEntity(client);
            repository.save(entity);
            log.debug("Client updated in cache: {}", client.getClientId());
        } catch (Exception e) {
            log.error("Failed to update client in cache: {}", client.getClientId(), e);
        }
    }

    @Override
    public void evict(String clientId) {
        try {
            repository.deleteById(clientId);
            log.debug("Client evicted from cache: {}", clientId);
        } catch (Exception e) {
            log.error("Failed to evict client from cache: {}", clientId, e);
        }
    }

    @Override
    public void evictAll() {
        try {
            long count = repository.count();
            repository.deleteAll();
            log.debug("All clients evicted from cache. Count: {}", count);
        } catch (Exception e) {
            log.error("Failed to evict all clients from cache", e);
        }
    }
}
