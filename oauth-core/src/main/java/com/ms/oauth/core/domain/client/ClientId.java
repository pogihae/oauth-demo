package com.ms.oauth.core.domain.client;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

/**
 * OAuth Client의 식별자를 나타내는 Value Object
 */
@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClientId implements Serializable {

    private String value;

    private ClientId(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("ClientId cannot be null or blank");
        }
        this.value = value;
    }

    public static ClientId of(String value) {
        return new ClientId(value);
    }

    public static ClientId generate() {
        return new ClientId(UUID.randomUUID().toString());
    }

    @Override
    public String toString() {
        return value;
    }
}
