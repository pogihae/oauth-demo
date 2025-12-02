package com.ms.oauth.core.application.port.in.client;

import com.ms.oauth.core.application.command.CreateClientCommand;
import com.ms.oauth.core.domain.client.Client;

/**
 * OAuth Client 등록 Use Case (Inbound Port)
 */
public interface CreateClientUseCase {
    Client register(CreateClientCommand command);
}
