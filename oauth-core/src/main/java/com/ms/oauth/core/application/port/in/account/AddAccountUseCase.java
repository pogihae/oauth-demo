package com.ms.oauth.core.application.port.in.account;

import com.ms.oauth.core.domain.account.Account;

/**
 * Account 추가 Use Case (Inbound Port)
 */
public interface AddAccountUseCase {
    Account addAccount(AddAccountCommand command);
}
