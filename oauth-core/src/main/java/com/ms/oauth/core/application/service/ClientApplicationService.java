package com.ms.oauth.core.application.service;

import com.ms.oauth.core.application.port.in.client.RegisterClientCommand;
import com.ms.oauth.core.application.port.in.client.RegisterClientUseCase;
import com.ms.oauth.core.domain.client.*;
import com.ms.oauth.core.domain.customer.CustomerId;
import com.ms.oauth.core.application.port.out.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * OAuth Client Application Service
 * Use Case를 구현하고 비즈니스 흐름을 조율
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClientApplicationService implements RegisterClientUseCase {

    private static final int MAX_CLIENTS_PER_CUSTOMER = 3;

    private final ClientRepository clientRepository;
    private final CustomerRepository customerRepository;

    /**
     * OAuth Client 등록
     * 비즈니스 규칙: Customer당 최대 3개까지 가능
     */
    @Override
    @Transactional
    public Client register(RegisterClientCommand command) {
        command.validate();

        // Customer 존재 확인
        CustomerId customerId = CustomerId.of(command.getCustomerId());
        if (!customerRepository.existsById(customerId)) {
            throw new IllegalArgumentException("Customer not found: " + command.getCustomerId());
        }

        // Customer의 Client 개수 확인
        long clientCount = clientRepository.countByCustomerId(customerId);
        if (clientCount >= MAX_CLIENTS_PER_CUSTOMER) {
            throw new IllegalStateException(
                String.format("Customer cannot have more than %d clients", MAX_CLIENTS_PER_CUSTOMER)
            );
        }

        // Client Name 중복 확인
        if (clientRepository.existsByClientName(command.getClientName())) {
            throw new IllegalArgumentException("Client name already exists: " + command.getClientName());
        }

        // Client 생성
        Client client;
        if (command.getClientSettings() != null || command.getTokenSettings() != null) {
            client = Client.create(
                customerId,
                command.getClientName(),
                command.getClientSecret(),
                command.getRedirectUris(),
                command.getGrantTypes(),
                command.getScopes(),
                command.getClientSettings(),
                command.getTokenSettings()
            );
        } else {
            client = Client.create(
                customerId,
                command.getClientName(),
                command.getClientSecret(),
                command.getRedirectUris(),
                command.getGrantTypes(),
                command.getScopes()
            );
        }

        // 저장
        return clientRepository.save(client);
    }

    /**
     * Client 조회
     */
    public Client getClient(String clientId) {
        return clientRepository.findById(ClientId.of(clientId))
            .orElseThrow(() -> new IllegalArgumentException("Client not found: " + clientId));
    }

    /**
     * Client 정보 업데이트
     */
    @Transactional
    public Client updateClient(
        String clientId,
        String clientName,
        Set<String> redirectUris,
        Set<GrantType> grantTypes,
        Set<String> scopes
    ) {
        Client client = getClient(clientId);

        // Client Name 변경 시 중복 확인
        if (!client.getClientName().equals(clientName) && clientRepository.existsByClientName(clientName)) {
            throw new IllegalArgumentException("Client name already exists: " + clientName);
        }

        client.updateClientInfo(clientName, redirectUris, grantTypes, scopes);
        return clientRepository.save(client);
    }

    /**
     * Client Secret 재발급
     */
    @Transactional
    public Client regenerateClientSecret(String clientId, String newClientSecret) {
        Client client = getClient(clientId);
        client.regenerateClientSecret(newClientSecret);
        return clientRepository.save(client);
    }

    /**
     * Client Settings 업데이트
     */
    @Transactional
    public Client updateClientSettings(String clientId, ClientSettings clientSettings) {
        Client client = getClient(clientId);
        client.updateClientSettings(clientSettings);
        return clientRepository.save(client);
    }

    /**
     * Token Settings 업데이트
     */
    @Transactional
    public Client updateTokenSettings(String clientId, TokenSettings tokenSettings) {
        Client client = getClient(clientId);
        client.updateTokenSettings(tokenSettings);
        return clientRepository.save(client);
    }

    /**
     * Client 활성화
     */
    @Transactional
    public Client activateClient(String clientId) {
        Client client = getClient(clientId);
        client.activate();
        return clientRepository.save(client);
    }

    /**
     * Client 비활성화
     */
    @Transactional
    public Client deactivateClient(String clientId) {
        Client client = getClient(clientId);
        client.deactivate();
        return clientRepository.save(client);
    }

    /**
     * Client 삭제
     */
    @Transactional
    public void deleteClient(String clientId) {
        clientRepository.deleteById(ClientId.of(clientId));
    }
}
