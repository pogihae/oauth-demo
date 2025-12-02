package com.ms.oauth.core.application.command;

import com.ms.oauth.core.domain.client.ClientSettings;
import com.ms.oauth.core.domain.client.GrantType;
import com.ms.oauth.core.domain.client.TokenSettings;
import com.ms.oauth.core.common.validation.ValidName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

/**
 * OAuth Client 등록 Command
 */
@Getter
@Builder
public class CreateClientCommand {
    @NotBlank(message = "Customer ID is required")
    private final String customerId;

    @ValidName
    private final String clientName;

    private final String clientSecret;

    @NotEmpty(message = "At least one redirect URI is required")
    private final Set<String> redirectUris;

    @NotEmpty(message = "At least one grant type is required")
    private final Set<GrantType> grantTypes;

    @NotEmpty(message = "At least one scope is required")
    private final Set<String> scopes;

    private final ClientSettings clientSettings;
    private final TokenSettings tokenSettings;

    public void validate() {
        if (customerId == null || customerId.isBlank()) {
            throw new IllegalArgumentException("Customer ID is required");
        }
        if (clientName == null || clientName.isBlank()) {
            throw new IllegalArgumentException("Client name is required");
        }
        if (clientSecret == null || clientSecret.isBlank()) {
            throw new IllegalArgumentException("Client secret is required");
        }
        if (redirectUris == null || redirectUris.isEmpty()) {
            throw new IllegalArgumentException("Redirect URIs are required");
        }
        if (grantTypes == null || grantTypes.isEmpty()) {
            throw new IllegalArgumentException("Grant types are required");
        }
        if (scopes == null || scopes.isEmpty()) {
            throw new IllegalArgumentException("Scopes are required");
        }
    }
}
