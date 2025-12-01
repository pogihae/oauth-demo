package com.ms.oauth.infrastructure.redis;

import com.ms.oauth.core.domain.client.Client;
import com.ms.oauth.core.domain.client.ClientId;
import com.ms.oauth.infrastructure.redis.port.ClientCachePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Optional;

/**
 * Client Cache Adapter (Infrastructure Layer)
 * Redis를 사용한 ClientCachePort 구현체
 * Hexagonal Architecture의 Outbound Adapter
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ClientCacheAdapter implements ClientCachePort {

    private static final String CACHE_PREFIX = "oauth:client:";
    private static final Duration CACHE_TTL = Duration.ofHours(24);

    private final RedisTemplate<String, Client> clientRedisTemplate;

    @Override
    public Optional<Client> findById(ClientId clientId) {
        try {
            String key = generateKey(clientId);
            Client client = clientRedisTemplate.opsForValue().get(key);
            if (client != null) {
                log.debug("Cache hit for client: {}", clientId);
            } else {
                log.debug("Cache miss for client: {}", clientId);
            }
            return Optional.ofNullable(client);
        } catch (Exception e) {
            log.error("Failed to get client from cache: {}", clientId, e);
            return Optional.empty();
        }
    }

    @Override
    public void save(Client client) {
        try {
            String key = generateKey(client.getId());
            clientRedisTemplate.opsForValue().set(key, client, CACHE_TTL);
            log.debug("Client saved to cache: {}", client.getId());
        } catch (Exception e) {
            log.error("Failed to save client to cache: {}", client.getId(), e);
        }
    }

    @Override
    public void update(Client client) {
        try {
            String key = generateKey(client.getId());
            clientRedisTemplate.opsForValue().set(key, client, CACHE_TTL);
            log.debug("Client updated in cache: {}", client.getId());
        } catch (Exception e) {
            log.error("Failed to update client in cache: {}", client.getId(), e);
        }
    }

    @Override
    public void evict(ClientId clientId) {
        try {
            String key = generateKey(clientId);
            clientRedisTemplate.delete(key);
            log.debug("Client evicted from cache: {}", clientId);
        } catch (Exception e) {
            log.error("Failed to evict client from cache: {}", clientId, e);
        }
    }

    @Override
    public void evictAll() {
        try {
            var keys = clientRedisTemplate.keys(CACHE_PREFIX + "*");
            if (keys != null && !keys.isEmpty()) {
                clientRedisTemplate.delete(keys);
                log.debug("All clients evicted from cache. Count: {}", keys.size());
            }
        } catch (Exception e) {
            log.error("Failed to evict all clients from cache", e);
        }
    }

    private String generateKey(ClientId clientId) {
        return CACHE_PREFIX + clientId.getValue();
    }
}
