package com.ms.oauth.core.application.command;

import com.ms.oauth.core.common.validation.*;
import com.ms.oauth.core.domain.client.ClientSettings;
import com.ms.oauth.core.domain.client.GrantType;
import com.ms.oauth.core.domain.client.TokenSettings;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

/**
 * OAuth Client 등록 Command
 */
public record CreateClientCommand(

        @NotBlank
        @ValidClientId
        String clientId,

        @NotBlank
        @ValidName
        String clientName,

        @ValidClientSecret
        String clientSecret,

        @NotEmpty(message = "Redirect URIs are required")
        @Size(max = 5, message = "Cannot exceed 5 redirect URIs")
        Set<@ValidRedirectUri String> redirectUris,

        @NotEmpty(message = "Grant types are required")
        Set<@NotNull GrantType> grantTypes,

        @NotEmpty(message = "Scopes are required")
        Set<@ValidScope String> scopes,

        ClientSettings clientSettings,

        TokenSettings tokenSettings
) {

    @AssertTrue(message = "Authorization Code grant requires at least one redirect URI")
    private boolean hasValidRedirectUrisForAuthorizationCode() {
        if (grantTypes != null && grantTypes.contains(GrantType.AUTHORIZATION_CODE)) {
            return redirectUris != null && !redirectUris.isEmpty();
        }
        return true;
    }
}
