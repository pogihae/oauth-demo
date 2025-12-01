package com.ms.oauth.adapter.jpa;

import com.ms.oauth.core.domain.account.Account;
import com.ms.oauth.core.domain.account.AccountId;
import com.ms.oauth.core.application.port.out.AccountRepository;
import com.ms.oauth.core.domain.account.AccountStatus;
import com.ms.oauth.core.domain.customer.CustomerId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Account Repository JPA 구현체 (Infrastructure Adapter)
 * Domain Layer의 AccountRepository 인터페이스를 구현
 */
@Repository
public interface AccountJpaRepository extends JpaRepository<Account, AccountId>, AccountRepository {

    @Override
    Optional<Account> findByEmail(String email);

    @Override
    List<Account> findAllByCustomerId(CustomerId customerId);

    @Override
    List<Account> findAllByCustomerIdAndStatus(CustomerId customerId, AccountStatus status);

    @Override
    long countByCustomerId(CustomerId customerId);

    @Override
    boolean existsByEmail(String email);
}
