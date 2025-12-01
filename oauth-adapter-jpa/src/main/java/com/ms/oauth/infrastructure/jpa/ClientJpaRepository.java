package com.ms.oauth.adapter.jpa;

import com.ms.oauth.core.domain.client.Client;
import com.ms.oauth.core.domain.client.ClientId;
import com.ms.oauth.core.application.port.out.ClientRepository;
import com.ms.oauth.core.domain.client.ClientStatus;
import com.ms.oauth.core.domain.customer.CustomerId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * OAuth Client Repository JPA 구현체 (Infrastructure Adapter)
 * Domain Layer의 ClientRepository 인터페이스를 구현
 */
@Repository
public interface ClientJpaRepository extends JpaRepository<Client, ClientId>, ClientRepository {

    @Override
    Optional<Client> findByClientName(String clientName);

    @Override
    List<Client> findAllByCustomerId(CustomerId customerId);

    @Override
    List<Client> findAllByCustomerIdAndStatus(CustomerId customerId, ClientStatus status);

    @Override
    long countByCustomerId(CustomerId customerId);

    @Override
    List<Client> findAllByStatus(ClientStatus status);

    @Override
    boolean existsByClientName(String clientName);
}
