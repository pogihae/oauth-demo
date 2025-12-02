package com.ms.oauth.core.application.port.in.account;

import com.ms.oauth.core.domain.account.Account;

import java.util.Optional;

/**
 * Account 조회 Query (Inbound Port)
 */
public interface GetAccountQuery {

    /**
     * ID로 Account 조회
     */
    Optional<Account> getAccountById(String accountId);

    /**
     * Email로 Account 조회
     */
    Optional<Account> getAccountByEmail(String email);

    /**
     * PhoneNo로 Account 조회
     */
    Optional<Account> getAccountByPhoneNo(String phoneNo);

}
