package com.ms.oauth.core.application.port.in.account;

import com.ms.oauth.core.domain.common.validation.ValidEmail;
import com.ms.oauth.core.domain.common.validation.ValidName;
import com.ms.oauth.core.domain.common.validation.ValidPassword;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

/**
 * Account 추가 Command
 */
@Getter
@Builder
public class AddAccountCommand {
    @NotBlank(message = "Customer ID is required")
    private final String customerId;

    @ValidName
    private final String accountName;

    @ValidEmail
    private final String email;

    @ValidPassword
    private final String password;
}
