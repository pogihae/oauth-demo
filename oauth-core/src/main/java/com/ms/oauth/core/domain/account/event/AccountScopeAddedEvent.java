package com.ms.oauth.core.domain.account.event;

import com.ms.oauth.core.domain.account.model.Account;
import com.ms.oauth.core.domain.client.Client;
import com.ms.oauth.core.domain.scope.Scope;

public record AccountScopeAddedEvent(
        Account account,
        Scope scope
) {
}
