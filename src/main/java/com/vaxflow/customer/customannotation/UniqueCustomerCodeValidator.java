package com.vaxflow.customer.customannotation;

import com.vaxflow.customer.entity.KhachHang;
import com.vaxflow.customer.repository.KhachHangRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UniqueCustomerCodeValidator implements ConstraintValidator<UniqueCustomerCode, KhachHang> {

    @Autowired
    private KhachHangRepository khachHangRepository;

    // 1. Constructor mặc định cho Hibernate Validator Reflection
    public UniqueCustomerCodeValidator() {
    }

    // 2. Constructor Injection cho Spring Container
    public UniqueCustomerCodeValidator(KhachHangRepository khachHangRepository) {
        this.khachHangRepository = khachHangRepository;
    }

    @Override
    public boolean isValid(KhachHang customer, ConstraintValidatorContext context) {
        if (khachHangRepository == null || customer == null || customer.getMaKhachHang() == null || customer.getMaKhachHang().trim().isEmpty()) {
            return true;
        }

        String code = customer.getMaKhachHang().trim();
        Optional<KhachHang> existingCustomer = khachHangRepository.findByMaKhachHang(code);

        if (existingCustomer.isEmpty()) {
            return true;
        }

        if (customer.getId() != null && customer.getId().equals(existingCustomer.get().getId())) {
            return true;
        }

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                .addPropertyNode("maKhachHang")
                .addConstraintViolation();

        return false;
    }
}