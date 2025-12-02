package com.ms.oauth.core.application.port.out;

import com.ms.oauth.core.domain.account.Account;
import jakarta.validation.constraints.NotBlank;

import java.util.Optional;

/**
 * Account Repository Port (Outbound Port)
 * Infrastructure Layer에서 구현됨
 */
public interface AccountOutPort {

    /**
     * Account 저장
     */
    Account save(Account account);

    /**
     * Account 조회 by ID
     */
    Optional<Account> findById(String accountId);

    /**
     * Account 조회 by Email
     */
    Optional<Account> findByEmail(String email);

    /**
     * Account 조회 by PhoneNo
     */
    Optional<Account> findByPhoneNo(String phoneNo);

    /**
     * Account ID 존재 여부 확인
     */
    boolean existsById(String accountId);

    /**
     * Phone No 존재 여부 확인
     */
    boolean existsByPhoneNo(String phoneNo);

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
    void deleteById(String accountId);

}
