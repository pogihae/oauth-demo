package com.ms.oauth.core.domain.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.lang.annotation.*;

/**
 * Password 유효성 검증을 위한 복합 어노테이션
 * @NotBlank + @Size(min=8) 통합
 */
@NotBlank(message = "Password is required")
@Size(min = 8, message = "Password must be at least 8 characters")
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface ValidPassword {
    String message() default "Invalid password";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
