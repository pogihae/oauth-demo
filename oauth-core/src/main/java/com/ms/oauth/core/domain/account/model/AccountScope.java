package com.ms.oauth.core.domain.account.model;

import com.ms.oauth.core.common.jpa.BaseEntity;
import com.ms.oauth.core.domain.scope.Scope;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Getter
@EqualsAndHashCode(callSuper = false, of = {"account", "scope"})
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountScope extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scope_id")
    private Scope scope;

    public AccountScope(Account account, Scope scope) {
        Assert.notNull(account, "account must not be null");
        Assert.notNull(scope, "scope must not be null");

        this.account = account;
        this.scope = scope;
    }
}
