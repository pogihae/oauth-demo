package com.ms.oauth.infrastructure.redis.port;

import com.ms.oauth.core.domain.client.Client;
import com.ms.oauth.core.domain.client.ClientId;

import java.util.Optional;

/**
 * Client Cache Port (Infrastructure Layer Interface)
 * Application Module에서 이 인터페이스에 의존
 * Redis Adapter에서 구현
 */
public interface ClientCachePort {

    /**
     * Cache에서 Client 조회
     */
    Optional<Client> findById(ClientId clientId);

    /**
     * Client를 Cache에 저장
     */
    void save(Client client);

    /**
     * Client를 Cache에서 업데이트
     */
    void update(Client client);

    /**
     * Cache에서 Client 삭제
     */
    void evict(ClientId clientId);

    /**
     * 모든 Client Cache 삭제
     */
    void evictAll();
}
