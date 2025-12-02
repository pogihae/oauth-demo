package com.ms.oauth.core.application.service;

import com.ms.oauth.core.application.command.CreateAccountCommand;
import com.ms.oauth.core.application.port.in.account.*;
import com.ms.oauth.core.application.port.in.account.EncodePasswordPort;
import com.ms.oauth.core.application.port.out.ClientOutPort;
import com.ms.oauth.core.common.exception.ErrorCode;
import com.ms.oauth.core.common.exception.ServerException;
import com.ms.oauth.core.domain.account.Account;
import com.ms.oauth.core.application.port.out.AccountOutPort;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

/**
 * Account Application Service
 * Use Case를 구현하고 비즈니스 흐름을 조율
 */
@Validated
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountService implements CreateAccountUseCase, GetAccountQuery {

    private final AccountOutPort accountOutPort;

    private final ClientOutPort clientOutPort;

    private final EncodePasswordPort encodePasswordPort;

    /**
     * Account 생성
     */
    @Override
    @Transactional
    public Account addAccount(@Valid CreateAccountCommand command) {

        // Account ID 중복 확인
        if (accountOutPort.existsById(command.accountId())) {
            throw new ServerException(ErrorCode.INVALID_ARGUMENT, "Account ID already exists");
        }

        // 이메일 중복 확인
        if (accountOutPort.existsByEmail(command.email())) {
            throw new ServerException(ErrorCode.INVALID_ARGUMENT, "Email already exists");
        }

        // 휴대전화 중복 확인
        if (accountOutPort.existsByPhoneNo(command.phoneNo())) {
            throw new ServerException(ErrorCode.INVALID_ARGUMENT, "Phone number already exists");
        }

        // 유효한 Client ID 확인
        if (!clientOutPort.validateIds(command.accessibleClientIds())) {
            throw new ServerException(ErrorCode.INVALID_ARGUMENT, "Invalid Client IDs");
        }

        // 비밀번호 암호화
        String encodedPassword = encodePasswordPort.encodePassword(command.password());

        Account account = Account.create(
                command.accountId(),
                command.name(),
                command.email(),
                command.phoneNo(),
                encodedPassword,
                command.accessibleClientIds()
        );

        return accountOutPort.save(account);
    }

    @Override
    public Optional<Account> getAccountById(String accountId) {
        return accountOutPort.findById(accountId);
    }

    @Override
    public Optional<Account> getAccountByEmail(String email) {
        return accountOutPort.findByEmail(email);
    }

    @Override
    public Optional<Account> getAccountByPhoneNo(String phoneNo) {
        return accountOutPort.findByPhoneNo(phoneNo);
    }
}
