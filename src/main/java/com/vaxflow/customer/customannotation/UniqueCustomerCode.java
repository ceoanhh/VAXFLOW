package com.vaxflow.customer.customannotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueCustomerCodeValidator.class)
@Target({ElementType.TYPE}) // Đặt cấp TYPE (Class) để truy cập được cả id và maKhachHang
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueCustomerCode {
    String message() default "Mã khách hàng này đã tồn tại trong hệ thống";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}