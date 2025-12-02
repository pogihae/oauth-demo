package com.ms.oauth.core.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.lang.annotation.*;

/**
 * OAuth Scope 유효성 검증을 위한 어노테이션
 * - 공백 없음
 * - 영문, 숫자, 언더스코어, 점, 하이픈, 콜론만 허용
 * - 최대 50자
 */
@NotBlank(message = "Scope cannot be blank")
@Size(max = 50, message = "Scope cannot exceed 50 characters")
@Constraint(validatedBy = ScopeValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidScope {
    String message() default "Invalid scope format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
