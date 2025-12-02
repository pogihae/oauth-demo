package com.ms.oauth.core.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.lang.annotation.*;

/**
 * Client Secret 유효성 검증을 위한 복합 어노테이션
 * @NotBlank + @Size(min=16, max=255) 통합
 */
@NotBlank(message = "Client Secret is required")
@Size(min = 16, max = 255, message = "Client Secret must be between 16 and 255 characters")
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface ValidClientSecret {
    String message() default "Invalid Client Secret";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
