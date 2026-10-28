package com.vaxflow.customer.customannotation;

import com.vaxflow.customer.entity.KhachHang;
import com.vaxflow.customer.repository.KhachHangRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UniquePhoneValidator implements ConstraintValidator<UniquePhone, KhachHang> {

    @Autowired
    private KhachHangRepository khachHangRepository;

    // 1. Constructor mặc định bắt buộc cho Hibernate Validator Reflection
    public UniquePhoneValidator() {
    }

    // 2. Constructor Injection cho Spring Container
    public UniquePhoneValidator(KhachHangRepository khachHangRepository) {
        this.khachHangRepository = khachHangRepository;
    }

    @Override
    public boolean isValid(KhachHang customer, ConstraintValidatorContext context) {
        // Kiểm tra null an toàn: Nếu repository null (khi Hibernate tự new) hoặc dữ liệu trống -> Bỏ qua
        if (khachHangRepository == null || customer == null || customer.getSdt() == null || customer.getSdt().trim().isEmpty()) {
            return true;
        }

        String phone = customer.getSdt().trim();
        Optional<KhachHang> existingCustomer = khachHangRepository.findBySdt(phone);

        // 1. Số điện thoại chưa từng tồn tại -> Hợp lệ
        if (existingCustomer.isEmpty()) {
            return true;
        }

        // 2. Trường hợp CẬP NHẬT: Số điện thoại trùng nhưng thuộc về chính ID đang chỉnh sửa -> Hợp lệ
        if (customer.getId() != null && customer.getId().equals(existingCustomer.get().getId())) {
            return true;
        }

        // 3. Trùng SĐT với khách hàng khác -> Gắn thông báo lỗi vào trường "sdt"
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                .addPropertyNode("sdt")
                .addConstraintViolation();

        return false;
    }
}