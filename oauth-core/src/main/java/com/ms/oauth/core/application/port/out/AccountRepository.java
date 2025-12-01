package com.ms.oauth.core.application.port.out;

import com.ms.oauth.core.domain.account.Account;
import com.ms.oauth.core.domain.account.AccountId;
import com.ms.oauth.core.domain.account.AccountStatus;
import com.ms.oauth.core.domain.customer.CustomerId;

import java.util.List;
import java.util.Optional;

/**
 * Account Repository Port (Outbound Port)
 * Infrastructure Layer에서 구현됨
 */
public interface AccountRepository {

    /**
     * Account 저장
     */
    Account save(Account account);

    /**
     * Account 조회 by ID
     */
    Optional<Account> findById(AccountId accountId);

    /**
     * Account 조회 by Email
     */
    Optional<Account> findByEmail(String email);

    /**
     * Customer의 모든 Account 조회
     */
    List<Account> findAllByCustomerId(CustomerId customerId);

    /**
     * Customer의 활성 Account 조회
     */
    List<Account> findAllByCustomerIdAndStatus(CustomerId customerId, AccountStatus status);

    /**
     * Customer의 Account 개수 조회
     */
    long countByCustomerId(CustomerId customerId);

    /**
     * Account 존재 여부 확인
     */
    boolean existsById(AccountId accountId);

    /**
     * Email 존재 여부 확인
     */
    boolean existsByEmail(String email);

    /**
     * Account 삭제
     */
    void delete(Account account);

    /**
     * Account 삭제 by ID
     */
    void deleteById(AccountId accountId);
}
