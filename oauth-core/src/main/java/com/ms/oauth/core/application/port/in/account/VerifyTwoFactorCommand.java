package com.ms.oauth.core.application.port.in.account;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

/**
 * 2차 인증 검증 Command
 */
@Getter
@Builder
public class VerifyTwoFactorCommand {

    @NotBlank(message = "Account ID is required")
    private final String accountId;

    @NotBlank(message = "Verification code is required")
    private final String verificationCode;

    @NotBlank(message = "Client ID is required")
    private final String clientId;

    public void validate() {
        if (accountId == null || accountId.isBlank()) {
            throw new IllegalArgumentException("Account ID is required");
        }
        if (verificationCode == null || verificationCode.isBlank()) {
            throw new IllegalArgumentException("Verification code is required");
        }
        if (clientId == null || clientId.isBlank()) {
            throw new IllegalArgumentException("Client ID is required");
        }
    }
}
