package com.ms.oauth.app.admin.application;

import com.ms.oauth.core.domain.client.Client;
import com.ms.oauth.core.domain.client.ClientId;
import com.ms.oauth.core.application.port.out.ClientRepository;
import com.ms.oauth.core.domain.customer.CustomerId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Client 조회 Service (Admin 전용)
 * 조회 시 캐시에 추가하지 않음 (Admin은 최신 데이터를 직접 DB에서 조회)
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClientAdminQueryService {

    private final ClientRepository clientRepository;

    /**
     * ID로 Client 조회 (캐시 사용 안함)
     */
    public Optional<Client> getClientById(String clientId) {
        return clientRepository.findById(ClientId.of(clientId));
    }

    /**
     * Client Name으로 Client 조회
     */
    public Optional<Client> getClientByName(String clientName) {
        return clientRepository.findByClientName(clientName);
    }

    /**
     * Customer의 모든 Client 조회
     */
    public List<Client> getClientsByCustomerId(String customerId) {
        return clientRepository.findAllByCustomerId(CustomerId.of(customerId));
    }

    /**
     * 모든 Client 조회
     */
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }
}
