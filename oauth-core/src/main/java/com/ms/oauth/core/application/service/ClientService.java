package com.ms.oauth.core.application.service;

import com.ms.oauth.core.application.port.in.client.RegisterClientCommand;
import com.ms.oauth.core.application.port.in.client.CreateClientUseCase;
import com.ms.oauth.core.domain.client.*;
import com.ms.oauth.core.domain.customer.CustomerId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * OAuth Client Application Service
 * Use Case를 구현하고 비즈니스 흐름을 조율
 */
@Validated
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClientService implements CreateClientUseCase {

    /**
     * OAuth Client 등록
     */
    @Override
    @Transactional
    public Client register(@Valid RegisterClientCommand command) {

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
}
