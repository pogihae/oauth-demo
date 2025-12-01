package com.ms.oauth.core.domain.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.lang.annotation.*;

/**
 * Name 유효성 검증을 위한 복합 어노테이션
 * @NotBlank + @Size(max=100) 통합
 */
@NotBlank(message = "Name is required")
@Size(max = 100, message = "Name cannot exceed 100 characters")
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface ValidName {
    String message() default "Invalid name";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
