package com.ms.oauth.core.application.port.in.account;

import com.ms.oauth.core.application.command.CreateAccountCommand;
import com.ms.oauth.core.domain.account.Account;

/**
 * Account 추가 Use Case (Inbound Port)
 */
public interface CreateAccountUseCase {
    Account addAccount(CreateAccountCommand command);
}
