package com.ms.oauth.core.application.port.in.client;

import com.ms.oauth.core.domain.account.Account;
import com.ms.oauth.core.domain.client.Client;

import java.util.Optional;

/**
 * Client 조회 Query (Inbound Port)
 */
public interface GetClientQuery {

    /**
     * ID로 Client 조회
     */
    Optional<Client> getClientById(String clientId);

}
