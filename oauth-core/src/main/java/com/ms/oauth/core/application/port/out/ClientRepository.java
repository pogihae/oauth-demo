package com.ms.oauth.core.application.port.out;

import com.ms.oauth.core.domain.client.Client;
import com.ms.oauth.core.domain.client.ClientId;
import com.ms.oauth.core.domain.client.ClientStatus;
import com.ms.oauth.core.domain.customer.CustomerId;

import java.util.List;
import java.util.Optional;

/**
 * OAuth Client Repository Port (Outbound Port)
 * Infrastructure Layer에서 구현됨
 */
public interface ClientRepository {

    /**
     * Client 저장
     */
    Client save(Client client);

    /**
     * Client 조회 by ID
     */
    Optional<Client> findById(ClientId clientId);

    /**
     * Client 조회 by Client Name
     */
    Optional<Client> findByClientName(String clientName);

    /**
     * Customer의 모든 Client 조회
     */
    List<Client> findAllByCustomerId(CustomerId customerId);

    /**
     * Customer의 활성 Client 조회
     */
    List<Client> findAllByCustomerIdAndStatus(CustomerId customerId, ClientStatus status);

    /**
     * Customer의 Client 개수 조회
     */
    long countByCustomerId(CustomerId customerId);

    /**
     * 모든 Client 조회
     */
    List<Client> findAll();

    /**
     * 활성 Client 조회
     */
    List<Client> findAllByStatus(ClientStatus status);

    /**
     * Client 존재 여부 확인
     */
    boolean existsById(ClientId clientId);

    /**
     * Client Name 존재 여부 확인
     */
    boolean existsByClientName(String clientName);

    /**
     * Client 삭제
     */
    void delete(Client client);

    /**
     * Client 삭제 by ID
     */
    void deleteById(ClientId clientId);
}
