package com.ms.oauth.core.application.port.in.customer;

import com.ms.oauth.core.domain.common.validation.ValidEmail;
import com.ms.oauth.core.domain.common.validation.ValidName;
import com.ms.oauth.core.domain.common.validation.ValidPhoneNumber;
import lombok.Builder;
import lombok.Getter;

/**
 * Customer 등록 Command
 */
@Getter
@Builder
public class RegisterCustomerCommand {
    @ValidName
    private final String customerName;

    @ValidEmail
    private final String email;

    @ValidPhoneNumber
    private final String phoneNumber;

    public void validate() {
        if (customerName == null || customerName.isBlank()) {
            throw new IllegalArgumentException("Customer name is required");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }
    }
}
