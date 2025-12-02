package com.ms.oauth.infrastructure.jpa.adapter;

import com.ms.oauth.core.application.port.out.AccountOutPort;
import com.ms.oauth.core.domain.account.Account;
import com.ms.oauth.infrastructure.jpa.entity.AccountJpaEntity;
import com.ms.oauth.infrastructure.jpa.mapper.AccountMapper;
import com.ms.oauth.infrastructure.jpa.repository.AccountJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Account Repository Adapter
 * Domain의 AccountRepository Port를 JPA로 구현하는 Adapter
 */
@Component
@RequiredArgsConstructor
public class AccountOutPortAdapter implements AccountOutPort {

    private final AccountJpaRepository jpaRepository;
    private final AccountMapper mapper;

    @Override
    public Account save(Account account) {
        AccountJpaEntity entity = mapper.toEntity(account);
        AccountJpaEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Account> findById(String accountId) {
        return jpaRepository.findById(accountId)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Account> findByEmail(String email) {
        return jpaRepository.findByEmail(email)
                .map(mapper::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    @Override
    public void delete(Account account) {
        AccountJpaEntity entity = mapper.toEntity(account);
        jpaRepository.delete(entity);
    }

    @Override
    public void deleteById(String accountId) {
        jpaRepository.deleteById(accountId);
    }
}
