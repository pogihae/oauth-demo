package com.ms.oauth.core.domain.account;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

/**
 * Account의 식별자를 나타내는 Value Object
 */
@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountId implements Serializable {

    private String value;

    private AccountId(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("AccountId cannot be null or blank");
        }
        this.value = value;
    }

    public static AccountId of(String value) {
        return new AccountId(value);
    }

    public static AccountId generate() {
        return new AccountId(UUID.randomUUID().toString());
    }

    @Override
    public String toString() {
        return value;
    }
}
