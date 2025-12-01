package com.ms.oauth.core.application.service;

import com.ms.oauth.core.application.port.in.account.*;
import com.ms.oauth.core.domain.account.Account;
import com.ms.oauth.core.domain.account.AccountId;
import com.ms.oauth.core.application.port.out.AccountRepository;
import com.ms.oauth.core.domain.client.Client;
import com.ms.oauth.core.domain.client.ClientId;
import com.ms.oauth.core.application.port.out.ClientRepository;
import com.ms.oauth.core.domain.customer.CustomerId;
import com.ms.oauth.core.application.port.out.CustomerRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Account Application Service
 * Use Case를 구현하고 비즈니스 흐름을 조율
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountApplicationService implements
    AddAccountUseCase,
    GetAccountQuery {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    /**
     * Account 추가
     * 비즈니스 규칙: Customer당 최대 5개까지 가능
     */
    @Override
    @Transactional
    public Account addAccount(@Valid AddAccountCommand command) {
        // Customer 존재 확인
        CustomerId customerId = CustomerId.of(command.getCustomerId());
        if (!customerRepository.existsById(customerId)) {
            throw new IllegalArgumentException("Customer not found: " + command.getCustomerId());
        }

        // Account 개수 확인 (비즈니스 규칙: 최대 5개)
        long accountCount = accountRepository.countByCustomerId(customerId);
        if (accountCount >= 5) {
            throw new IllegalStateException("Customer cannot have more than 5 accounts");
        }

        // 이메일 중복 확인
        if (accountRepository.existsByEmail(command.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + command.getEmail());
        }

        // Account 생성 (password는 이미 암호화된 것으로 가정)
        Account account = Account.create(
            customerId,
            command.getAccountName(),
            command.getEmail(),
            command.getPassword()
        );

        return accountRepository.save(account);
    }

    /**
     * Account 조회
     */
    public Account getAccount(String accountId) {
        return accountRepository.findById(AccountId.of(accountId))
            .orElseThrow(() -> new IllegalArgumentException("Account not found: " + accountId));
    }

    /**
     * Account 정보 업데이트
     */
    @Transactional
    public Account updateAccount(String accountId, String accountName, String email) {
        Account account = getAccount(accountId);

        // 이메일 변경 시 중복 확인
        if (!account.getEmail().equals(email) && accountRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists: " + email);
        }

        account.updateAccountName(accountName);
        account.updateEmail(email);
        return accountRepository.save(account);
    }

    /**
     * Account 활성화
     */
    @Transactional
    public Account activateAccount(String accountId) {
        Account account = getAccount(accountId);
        account.activate();
        return accountRepository.save(account);
    }

    /**
     * Account 비활성화
     */
    @Transactional
    public Account deactivateAccount(String accountId) {
        Account account = getAccount(accountId);
        account.deactivate();
        return accountRepository.save(account);
    }

    /**
     * Account 삭제
     */
    @Transactional
    public void deleteAccount(String accountId) {
        accountRepository.deleteById(AccountId.of(accountId));
    }

    /**
     * 비밀번호 변경
     */
    @Transactional
    public Account changePassword(String accountId, String newEncodedPassword) {
        Account account = getAccount(accountId);
        account.changePassword(newEncodedPassword);
        return accountRepository.save(account);
    }

    // ========== GetAccountQuery 구현 ==========

    @Override
    public Optional<Account> getAccountById(String accountId) {
        return accountRepository.findById(AccountId.of(accountId));
    }

    @Override
    public Optional<Account> getAccountByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    @Override
    public List<Account> getAccountsByCustomerId(String customerId) {
        return accountRepository.findAllByCustomerId(CustomerId.of(customerId));
    }
}
