package com.ms.oauth.core.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

/**
 * OAuth Scope 유효성 검증기
 * 영문, 숫자, 언더스코어, 점, 하이픈, 콜론만 허용
 */
public class ScopeValidator implements ConstraintValidator<ValidScope, String> {

    // OAuth 2.0 scope-token 규칙: NQCHAR = %x21 / %x23-5B / %x5D-7E
    // 간단하게 영문, 숫자, 언더스코어, 점, 하이픈, 콜론만 허용
    private static final Pattern SCOPE_PATTERN = Pattern.compile("^[a-zA-Z0-9_.:-]+$");

    @Override
    public void initialize(ValidScope constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return false;
        }

        // 공백이 포함되어 있으면 안됨
        if (value.contains(" ")) {
            return false;
        }

        // 패턴 검증
        return SCOPE_PATTERN.matcher(value).matches();
    }
}
