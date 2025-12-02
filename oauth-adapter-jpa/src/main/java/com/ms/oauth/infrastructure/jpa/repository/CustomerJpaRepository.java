package com.ms.oauth.infrastructure.jpa.repository;

import com.ms.oauth.core.domain.customer.Customer;
import com.ms.oauth.core.domain.customer.CustomerId;
import com.ms.oauth.core.domain.customer.CustomerStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Customer Repository JPA 구현체 (Infrastructure Adapter)
 * Domain Layer의 CustomerRepository 인터페이스를 구현
 */
@Repository
public interface CustomerJpaRepository extends JpaRepository<Customer, CustomerId>, CustomerRepository {

    @Override
    Optional<Customer> findByEmail(String email);

    @Override
    List<Customer> findAllByStatus(CustomerStatus status);

    @Override
    boolean existsByEmail(String email);
}
