package com.ms.oauth.core.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Size;

import java.lang.annotation.*;

/**
 * Client ID 유효성 검증을 위한 복합 어노테이션
 */
@Size(max = 50, message = "Client ID cannot exceed 50 characters")
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface ValidClientId {
    String message() default "Invalid Client ID";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
