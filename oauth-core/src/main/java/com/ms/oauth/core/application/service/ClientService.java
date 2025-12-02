package com.ms.oauth.core.application.service;

import com.ms.oauth.core.application.command.CreateClientCommand;
import com.ms.oauth.core.application.port.in.client.CreateClientUseCase;
import com.ms.oauth.core.application.port.in.client.EncodeClientPasswordPort;
import com.ms.oauth.core.application.port.in.client.GetClientQuery;
import com.ms.oauth.core.application.port.out.ClientOutPort;
import com.ms.oauth.core.domain.client.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

/**
 * OAuth Client Application Service
 * Use Case를 구현하고 비즈니스 흐름을 조율
 */
@Validated
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClientService implements CreateClientUseCase, GetClientQuery {

    private final ClientOutPort clientOutPort;

    private final EncodeClientPasswordPort encodeClientPasswordPort;

    /**
     * OAuth Client 등록
     */
    @Override
    @Transactional
    public Client createClient(@Valid CreateClientCommand command) {

        // 시크릿 암호화
        String encodedSecret = encodeClientPasswordPort.encodePassword(command.clientSecret());

        Client client = Client.create(
                command.clientId(),
                command.clientName(),
                encodedSecret,
                command.redirectUris(),
                command.grantTypes(),
                command.scopes(),
                command.clientSettings(),
                command.tokenSettings()
        );

        return clientOutPort.save(client);
    }

    @Override
    public Optional<Client> getClientById(String clientId) {
        return clientOutPort.findById(clientId);
    }
}
