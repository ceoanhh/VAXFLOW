package com.vaxflow.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

/**
 * Dich vu gui Email thong bao & khoi phuc mat khau he thong VaxFlow.
 */
@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);
    private final SecureRandom random = new SecureRandom();

    /**
     * Tao ma OTP ngau nhien 6 chu so
     */
    public String generateOtp() {
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    /**
     * Gui Email ma OTP khoi phuc mat khau
     */
    public boolean sendPasswordResetOtp(String toEmail, String fullName, String otp) {
        log.info("================================================================================");
        log.info(">>> [VAXFLOW EMAIL SYSTEM] ĐANG GỬI EMAIL KHÔI PHỤC MẬT KHẨU");
        log.info(">>> Gửi đến: {} (Họ tên: {})", toEmail, fullName);
        log.info(">>> Tiêu đề: [VaxFlow] Mã xác nhận khôi phục mật khẩu tài khoản");
        log.info(">>> Mã xác thực OTP: [{}] (Hiệu lực trong vòng 15 phút)", otp);
        log.info(">>> Nội dung: Xin chào {}, hệ thống nhận được yêu cầu đặt lại mật khẩu cho tài khoản của bạn. Vui lòng nhập mã OTP {} để hoàn tất.", fullName, otp);
        log.info("================================================================================");

        // Gia lap gui email thanh cong tren moi truong dev
        return true;
    }
}
