package com.ms.oauth.core.application.port.in.account;

/**
 * Account 인증(로그인) Use Case (Inbound Port)
 */
public interface AuthenticateAccountUseCase {

    /**
     * Account 인증
     * @return AuthenticationResult
     */
    AuthenticationResult authenticate(AuthenticateAccountCommand command);
}
