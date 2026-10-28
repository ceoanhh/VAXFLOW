package com.vaxflow.customer.customannotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniquePhoneValidator.class)
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniquePhone {
    String message() default "Số điện thoại này đã được sử dụng trên hệ thống";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}