package com.ms.oauth.core.application.command;

import com.ms.oauth.core.common.exception.ErrorCode;
import com.ms.oauth.core.common.exception.ServerException;
import com.ms.oauth.core.common.validation.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Set;

/**
 * Account 추가 Command
 */
public record AddAccountCommand(

        @NotBlank
        @ValidAccountId
        String accountId,

        @NotBlank
        @ValidName
        String name,

        @ValidEmail
        String email,

        @ValidPhoneNumber
        String phoneNo,

        @NotBlank
        @ValidPassword
        String password,

        @NotEmpty
        @Size(max = 5)
        Set<String> accessibleClientIds
) {

    @AssertTrue(message = "account requires email or phone number")
    private boolean hasEmailOrPhoneNo() {
        return StringUtils.hasText(email) || StringUtils.hasText(phoneNo);
    }

}
