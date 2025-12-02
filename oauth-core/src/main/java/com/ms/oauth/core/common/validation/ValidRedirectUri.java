package com.ms.oauth.core.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Size;

import java.lang.annotation.*;

/**
 * Redirect URI 유효성 검증을 위한 어노테이션
 * - http 또는 https로 시작
 * - 유효한 URI 형식
 * - 최대 200자
 */
@Size(max = 200, message = "Redirect URI cannot exceed 200 characters")
@Constraint(validatedBy = RedirectUriValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidRedirectUri {
    String message() default "Invalid Redirect URI format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
