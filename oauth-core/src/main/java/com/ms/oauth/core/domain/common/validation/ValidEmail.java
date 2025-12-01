package com.ms.oauth.core.domain.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.lang.annotation.*;

/**
 * Email 유효성 검증을 위한 복합 어노테이션
 * @NotBlank + @Email + @Size(max=100) 통합
 */
@NotBlank(message = "Email is required")
@Email(message = "Invalid email format")
@Size(max = 100, message = "Email cannot exceed 100 characters")
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface ValidEmail {
    String message() default "Invalid email";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
