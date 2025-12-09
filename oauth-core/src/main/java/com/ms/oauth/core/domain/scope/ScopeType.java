package com.ms.oauth.core.domain.scope;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum ScopeType {
    READ_WRITE(""),
    READ_ONLY("read")
    ;

    private final String suffix;

    public static ScopeType of(String suffix) {
        return Arrays.stream(ScopeType.values())
                .filter(scopeType -> scopeType.getSuffix().equals(suffix)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid scope type: " + suffix));
    }
}
