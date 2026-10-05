package com.vaxflow.core;

import com.vaxflow.core.entity.Admin;
import com.vaxflow.core.repository.AdminRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * DataSeeder dung chung cho toan bo team (Muc 3.7 trong tai lieu phan cong).
 * Chi chay khi Spring profile la 'dev'.
 * Moi thanh vien trong nhom se bo sung doan seed du lieu cua module minh vao day.
 */
@Component
@Profile("dev")
public class DataSeeder implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info(">>> Dang khoi chay DataSeeder (profile: dev)...");

        seedAdmin();

        // TODO [Person 5 - Truong]: seedVaccinesAndBatches();
        // TODO [Person 2 - Bao]: seedCustomersAndVaccinationRecords();
        // TODO [Person 3 - Khanh]: seedInvoicesAndPayments();
        // TODO [Person 4 - Lan Anh]: seedDepartmentsAndEmployees();

        log.info(">>> Hoan tat khoi tao du lieu mau (DataSeeder).");
    }

    /**
     * Person 1 (Bright): Khoi tao tai khoan quan tri vien mac dinh
     */
    private void seedAdmin() {
        String defaultUsername = "admin";
        if (!adminRepository.existsByTenDangNhap(defaultUsername)) {
            Admin admin = new Admin();
            admin.setTenDangNhap(defaultUsername);
            admin.setMatKhau(passwordEncoder.encode("admin123"));
            admin.setHoTen("Quản trị viên hệ thống");
            admin.setTrangThai(true);

            adminRepository.save(admin);
            log.info("=== [Person 1] Da tao tai khoan Admin mac dinh: admin / admin123 ===");
        } else {
            log.info("=== [Person 1] Tai khoan Admin '{}' da ton tai ===", defaultUsername);
        }
    }
}
