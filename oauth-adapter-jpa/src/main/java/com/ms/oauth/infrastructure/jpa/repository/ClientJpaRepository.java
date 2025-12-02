package com.ms.oauth.infrastructure.jpa.repository;

import com.ms.oauth.core.domain.client.ClientStatus;
import com.ms.oauth.infrastructure.jpa.entity.ClientJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Client JPA Repository Interface
 * Spring Data JPA를 사용한 데이터베이스 접근
 */
public interface ClientJpaRepository extends JpaRepository<ClientJpaEntity, String> {

    Optional<ClientJpaEntity> findByClientName(String clientName);

    List<ClientJpaEntity> findAllByStatus(ClientStatus status);

    boolean existsByClientName(String clientName);
}
