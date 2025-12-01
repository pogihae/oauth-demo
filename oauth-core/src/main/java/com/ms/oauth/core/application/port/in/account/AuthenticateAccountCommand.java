package com.ms.oauth.core.application.port.in.account;

import com.ms.oauth.core.domain.common.validation.ValidEmail;
import com.ms.oauth.core.domain.common.validation.ValidPassword;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

/**
 * Account 인증(로그인) Command
 */
@Getter
@Builder
public class AuthenticateAccountCommand {

    @ValidEmail
    private final String email;

    @ValidPassword
    private final String rawPassword;

    @NotBlank(message = "Client ID is required")
    private final String clientId;

    public void validate() {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (rawPassword == null || rawPassword.isBlank()) {
            throw new IllegalArgumentException("Password is required");
        }
        if (clientId == null || clientId.isBlank()) {
            throw new IllegalArgumentException("Client ID is required");
        }
    }
}
