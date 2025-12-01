package com.ms.oauth.core.domain.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.lang.annotation.*;

/**
 * Client Secret 유효성 검증을 위한 복합 어노테이션
 * @NotBlank + @Size(min=16) 통합
 */
@NotBlank(message = "Client secret is required")
@Size(min = 16, message = "Client secret must be at least 16 characters")
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface ValidClientSecret {
    String message() default "Invalid client secret";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
