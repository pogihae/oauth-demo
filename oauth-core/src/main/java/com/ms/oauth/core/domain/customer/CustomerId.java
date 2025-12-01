package com.ms.oauth.core.domain.customer;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

/**
 * Customer의 식별자를 나타내는 Value Object
 */
@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomerId implements Serializable {

    private String value;

    private CustomerId(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("CustomerId cannot be null or blank");
        }
        this.value = value;
    }

    public static CustomerId of(String value) {
        return new CustomerId(value);
    }

    public static CustomerId generate() {
        return new CustomerId(UUID.randomUUID().toString());
    }

    @Override
    public String toString() {
        return value;
    }
}
