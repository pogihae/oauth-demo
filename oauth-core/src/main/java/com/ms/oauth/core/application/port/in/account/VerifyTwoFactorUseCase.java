package com.ms.oauth.core.application.port.in.account;

/**
 * 2차 인증 검증 Use Case (Inbound Port)
 */
public interface VerifyTwoFactorUseCase {

    /**
     * 2차 인증 검증
     * @return 검증 성공 여부
     */
    boolean verifyTwoFactor(VerifyTwoFactorCommand command);
}
