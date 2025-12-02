package com.ms.oauth.core.application.port.out;

import com.ms.oauth.core.domain.client.Client;

import java.util.Optional;
import java.util.Set;

/**
 * OAuth Client Repository Port (Outbound Port)
 * Infrastructure Layer에서 구현됨
 */
public interface ClientOutPort {

    /**
     * Client 저장
     */
    Client save(Client client);

    /**
     * Client 조회 by ID
     */
    Optional<Client> findById(String clientId);

    /**
     * Client ID 검증용
     */
    boolean validateIds(Set<String> clientIds);

    /**
     * Client 삭제 by ID
     */
    void deleteById(String clientId);
}
