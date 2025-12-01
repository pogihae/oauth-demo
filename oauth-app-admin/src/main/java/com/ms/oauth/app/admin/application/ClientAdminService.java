package com.ms.oauth.app.admin.application;

import com.ms.oauth.core.domain.client.*;
import com.ms.oauth.adapter.redis.port.ClientCachePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * Client 수정 Service (Admin 전용)
 * 수정 시 캐시도 함께 업데이트하여 다른 서버(API, OAuth Server)의 캐시와 동기화
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ClientAdminService {

    private final ClientRepository clientRepository;
    private final ClientCachePort clientCachePort;

    /**
     * Client 정보 업데이트 (캐시도 함께 업데이트)
     */
    public Client updateClient(
        String clientId,
        String clientName,
        Set<String> redirectUris,
        Set<GrantType> grantTypes,
        Set<String> scopes
    ) {
        Client client = getClient(clientId);

        // Client Name 변경 시 중복 확인
        if (!client.getClientName().equals(clientName) &&
            clientRepository.existsByClientName(clientName)) {
            throw new IllegalArgumentException("Client name already exists: " + clientName);
        }

        client.updateClientInfo(clientName, redirectUris, grantTypes, scopes);
        Client updated = clientRepository.save(client);

        // 캐시 업데이트 (다른 서버의 캐시 동기화)
        clientCachePort.update(updated);

        return updated;
    }

    /**
     * Client Secret 재발급 (캐시도 함께 업데이트)
     */
    public Client regenerateClientSecret(String clientId, String newClientSecret) {
        Client client = getClient(clientId);
        client.regenerateClientSecret(newClientSecret);
        Client updated = clientRepository.save(client);

        // 캐시 업데이트
        clientCachePort.update(updated);

        return updated;
    }

    /**
     * Client Settings 업데이트 (캐시도 함께 업데이트)
     */
    public Client updateClientSettings(String clientId, ClientSettings clientSettings) {
        Client client = getClient(clientId);
        client.updateClientSettings(clientSettings);
        Client updated = clientRepository.save(client);

        // 캐시 업데이트
        clientCachePort.update(updated);

        return updated;
    }

    /**
     * Token Settings 업데이트 (캐시도 함께 업데이트)
     */
    public Client updateTokenSettings(String clientId, TokenSettings tokenSettings) {
        Client client = getClient(clientId);
        client.updateTokenSettings(tokenSettings);
        Client updated = clientRepository.save(client);

        // 캐시 업데이트
        clientCachePort.update(updated);

        return updated;
    }

    /**
     * Client 활성화 (캐시도 함께 업데이트)
     */
    public Client activateClient(String clientId) {
        Client client = getClient(clientId);
        client.activate();
        Client updated = clientRepository.save(client);

        // 캐시 업데이트
        clientCachePort.update(updated);

        return updated;
    }

    /**
     * Client 비활성화 (캐시도 함께 업데이트)
     */
    public Client deactivateClient(String clientId) {
        Client client = getClient(clientId);
        client.deactivate();
        Client updated = clientRepository.save(client);

        // 캐시 업데이트
        clientCachePort.update(updated);

        return updated;
    }

    /**
     * Client 삭제 (캐시에서도 삭제)
     */
    public void deleteClient(String clientId) {
        ClientId id = ClientId.of(clientId);
        clientRepository.deleteById(id);

        // 캐시에서 삭제
        clientCachePort.evict(id);
    }

    private Client getClient(String clientId) {
        return clientRepository.findById(ClientId.of(clientId))
            .orElseThrow(() -> new IllegalArgumentException("Client not found: " + clientId));
    }
}
