package com.ms.oauth.core.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Redirect URI 유효성 검증기
 */
public class RedirectUriValidator implements ConstraintValidator<ValidRedirectUri, String> {

    @Override
    public void initialize(ValidRedirectUri constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return false;
        }

        try {
            URI uri = new URI(value);
            String scheme = uri.getScheme();

            // http 또는 https만 허용
            if (scheme == null || (!scheme.equalsIgnoreCase("http") && !scheme.equalsIgnoreCase("https"))) {
                return false;
            }

            // host가 있어야 함
            if (uri.getHost() == null || uri.getHost().isBlank()) {
                return false;
            }

            return true;
        } catch (URISyntaxException e) {
            return false;
        }
    }
}
