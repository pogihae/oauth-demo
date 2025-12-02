package com.ms.oauth.infrastructure.jpa.repository;

import com.ms.oauth.infrastructure.jpa.entity.AccountJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Account JPA Repository Interface
 * Spring Data JPA를 사용한 데이터베이스 접근
 */
public interface AccountJpaRepository extends JpaRepository<AccountJpaEntity, String> {

    Optional<AccountJpaEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByPhoneNo(String phoneNo);
}
