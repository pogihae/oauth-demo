package com.ms.oauth.core.domain.account.model;

import com.ms.oauth.core.domain.account.event.AccountScopeAddedEvent;
import com.ms.oauth.core.domain.account.event.AccountScopeRemovedEvent;
import com.ms.oauth.core.domain.account.event.AccountCreatedEvent;
import com.ms.oauth.core.common.jpa.BaseEntity;
import com.ms.oauth.core.domain.account.event.AccountPasswordChangedEvent;
import com.ms.oauth.core.domain.client.Client;
import com.ms.oauth.core.domain.scope.Scope;
import com.ms.oauth.core.domain.scope.ScopeType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String accountId;

    @Column(nullable = false)
    @Setter private String name;

    @Setter private String email;

    @Setter private String phoneNo;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Setter private AccountStatus status = AccountStatus.ACTIVE;

    @Getter(AccessLevel.NONE)
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "account")
    private List<AccountScope> accountScopes = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Account parent;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "parent")
    private List<Account> children = new ArrayList<>();

    /**
     * 마지막 2차 인증 검증 시각
     */
    private LocalDateTime lastTwoFactorVerifiedAt;

    /**
     * 비밀번호 마지막 변경 시각
     */
    private LocalDateTime passwordChangedAt;

    /**
     * 로그인 실패 횟수
     */
    @Column(nullable = false)
    private int failedLoginAttempts = 0;

    /**
     * 계정 잠금 시각
     */
    private LocalDateTime lockedAt;

    /**
     * 마지막 로그인 시각
     */
    private LocalDateTime lastLoginAt;

    public void updatePassword(String password) {
        this.password = password;
        this.passwordChangedAt = LocalDateTime.now();
        registerEvent(new AccountPasswordChangedEvent(this));
    }

    public void addScope(Scope scope) {
        AccountScope accountScope = new AccountScope(this, scope);
        if (accountScopes.contains(accountScope)) {
            throw new IllegalArgumentException("Scope already contains");
        }
        accountScopes.add(accountScope);
        registerEvent(new AccountScopeAddedEvent(this, scope));
    }

    public void removeScope(Scope scope) {
        AccountScope accountScope = new AccountScope(this, scope);
        if (!accountScopes.contains(accountScope)) {
            throw new IllegalArgumentException("Scope not contains");
        }
        accountScopes.remove(accountScope);

        // 자식 스코프 삭제
        if (scope.getType() == ScopeType.READ_WRITE) {
            children.forEach(child -> child.removeAllScopesByKey(scope.getKey()));
        }

        registerEvent(new AccountScopeRemovedEvent(this, scope));
    }

    private void removeAllScopesByKey(String key) {
        List<AccountScope> toRemove = accountScopes.stream()
                .filter(as -> as.getScope().getKey().equals(key))
                .toList();
        accountScopes.removeAll(toRemove);
    }

    public boolean hasScope(Scope scope) {
        AccountScope accountScope = new AccountScope(this, scope);
        return accountScopes.contains(accountScope);
    }

    public boolean isMaster() {
        return parent == null;
    }

    public static Account create(String accountId, String name, String email, String phoneNo, String password, List<Scope> scopes) {
        Account account = new Account();
        account.accountId = accountId;
        account.name = name;
        account.email = email;
        account.phoneNo = phoneNo;
        account.password = password;
        scopes.forEach(account::addScope);
        account.registerEvent(new AccountCreatedEvent(account));
        return account;
    }
}
