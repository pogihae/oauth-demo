package com.ms.oauth.core.domain.account.event;

import com.ms.oauth.core.domain.account.model.Account;

public record AccountPasswordChangedEvent(
        Account account
) {
}
