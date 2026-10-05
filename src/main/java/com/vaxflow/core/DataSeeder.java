package com.vaxflow.core;

import com.vaxflow.core.entity.Admin;
import com.vaxflow.core.entity.Customer;
import com.vaxflow.core.entity.VaccinationRecord;
import com.vaxflow.core.repository.AdminRepository;
import com.vaxflow.core.repository.CustomerRepository;
import com.vaxflow.core.repository.VaccinationRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Profile("dev")
public class DataSeeder implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);

    private final AdminRepository adminRepository;
    private final CustomerRepository customerRepository;
    private final VaccinationRecordRepository vaccinationRecordRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(AdminRepository adminRepository,
                      CustomerRepository customerRepository,
                      VaccinationRecordRepository vaccinationRecordRepository,
                      PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.customerRepository = customerRepository;
        this.vaccinationRecordRepository = vaccinationRecordRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info(">>> Dang khoi chay DataSeeder (profile: dev)...");

        seedAdmin();
        seedCustomersAndVaccinationRecords();

        log.info(">>> Hoan tat khoi tao du lieu mau (DataSeeder).");
    }

    private void seedAdmin() {
        String defaultUsername = "admin";
        if (!adminRepository.existsByTenDangNhap(defaultUsername)) {
            Admin admin = new Admin();
            admin.setTenDangNhap(defaultUsername);
            admin.setMatKhau(passwordEncoder.encode("admin123"));
            admin.setHoTen("Quản trị viên hệ thống");
            admin.setTrangThai(true);

            adminRepository.save(admin);
            log.info("=== [Admin] Da tao tai khoan mac dinh: admin / admin123 ===");
        }
    }

    private void seedCustomersAndVaccinationRecords() {
        if (customerRepository.count() == 0) {
            // Khach hang 1: Be Nguyen Gia Bao (Tre em)
            Customer c1 = new Customer("Nguyễn Gia Bảo", LocalDate.of(2024, 8, 15), "Nam", "0912345678", "001204018992", "Số 18 Hoàng Quốc Việt, Cầu Giấy, Hà Nội");
            c1.setNguoiGiamHo("Nguyễn Văn Tuấn (Bố)");
            c1.setSoDienThoaiGiamHo("0912345678");
            c1.setTienSuBenh("Không có tiền sử dị ứng, tiền sử sinh non 37 tuần.");
            customerRepository.save(c1);

            createShot(c1, "Hexaxim (6 trong 1) - Sanofi (Pháp)", 1, LocalDate.of(2024, 10, 18), "HEX-8172", "BS. CKI Nguyễn Văn An", "ĐD. Lê Thị Mai", "DA_TIEM", "Bình thường, hơi quấy khóc nhẹ trong 12h đầu.");
            createShot(c1, "Synflorix (Phế cầu 10) - GSK (Bỉ)", 1, LocalDate.of(2024, 10, 18), "SYN-4412", "BS. CKI Nguyễn Văn An", "ĐD. Lê Thị Mai", "DA_TIEM", "Vết tiêm sưng nhẹ, tự hết sau 24h.");
            createShot(c1, "Hexaxim (6 trong 1) - Sanofi (Pháp)", 2, LocalDate.of(2024, 11, 20), "HEX-9021", "BS. CKI Phạm Quỳnh Trang", "ĐD. Trần Kim Dung", "DA_TIEM", "Sức khỏe ổn định, không sốt.");
            createShot(c1, "Hexaxim (6 trong 1) - Sanofi (Pháp)", 3, LocalDate.now().plusDays(10), "HEX-9943", "BS. CKI Nguyễn Văn An", "Chưa phân công", "HEN_TIEM", "Hẹn mũi 3 theo đúng phác đồ.");

            // Khach hang 2: Tran Thi Mai Anh (Nguoi lon)
            Customer c2 = new Customer("Trần Thị Mai Anh", LocalDate.of(1998, 4, 12), "Nữ", "0987654321", "001198005432", "Số 45 Lê Duẩn, Quận 1, TP. Hồ Chí Minh");
            c2.setTienSuBenh("Dị ứng nhẹ với phấn hoa.");
            customerRepository.save(c2);

            createShot(c2, "Gardasil 9 (HPV 9 chủng) - MSD (Mỹ)", 1, LocalDate.of(2025, 1, 10), "GARD-0912", "BS. ThS Lê Hoàng Nam", "ĐD. Nguyễn Bích Ngọc", "DA_TIEM", "Theo dõi 30 phút bình thường.");
            createShot(c2, "Gardasil 9 (HPV 9 chủng) - MSD (Mỹ)", 2, LocalDate.of(2025, 3, 12), "GARD-1055", "BS. ThS Lê Hoàng Nam", "ĐD. Nguyễn Bích Ngọc", "DA_TIEM", "Sức khỏe tốt.");
            createShot(c2, "Gardasil 9 (HPV 9 chủng) - MSD (Mỹ)", 3, LocalDate.now().plusMonths(2), "GARD-1120", "BS. ThS Lê Hoàng Nam", "Chưa phân công", "HEN_TIEM", "Mũi 3 hoàn thành phác đồ HPV.");

            // Khach hang 3: Le Van Hung (Nguoi cao tuoi)
            Customer c3 = new Customer("Lê Văn Hùng", LocalDate.of(1960, 11, 25), "Nam", "0903456789", "031060001234", "Số 86 Nguyễn Văn Linh, Hải Châu, Đà Nẵng");
            c3.setTienSuBenh("Tăng huyết áp nhẹ, đang dùng thuốc đều đặn.");
            customerRepository.save(c3);

            createShot(c3, "Prevenar 13 (Phế cầu 13) - Pfizer (Bỉ)", 1, LocalDate.of(2025, 6, 5), "PREV-7711", "BS. CKI Vũ Đức Thắng", "ĐD. Hoàng Yến", "DA_TIEM", "Huyết áp ổn định trước và sau tiêm.");
            createShot(c3, "Influvac Tetra (Cúm mùa) - Abbott (Hà Lan)", 1, LocalDate.now(), "INFL-2026-01", "BS. CKI Vũ Đức Thắng", "ĐD. Hoàng Yến", "DA_TIEM", "Tiêm định kỳ hàng năm cho người cao tuổi.");

            // Khach hang 4: Pham Thi Thu (Phu nu mang thai)
            Customer c4 = new Customer("Phạm Thị Thu", LocalDate.of(1995, 9, 8), "Nữ", "0971239988", "024195009876", "Số 102 Trường Chinh, Đống Đa, Hà Nội");
            c4.setTienSuBenh("Đang mang thai tuần thứ 26.");
            customerRepository.save(c4);

            createShot(c4, "Boostrix (Bạch hầu - Uốn ván - Ho gà nhắc) - GSK (Bỉ)", 1, LocalDate.now().minusDays(5), "BOOST-332", "BS. CKI Phạm Quỳnh Trang", "ĐD. Vũ Thùy Linh", "DA_TIEM", "Bảo vệ thai nhi phòng ho gà sớm.");

            log.info("=== Da khoi tao thanh cong 4 khach hang mau va cac mui tiem kem theo ===");
        }
    }

    private void createShot(Customer customer, String tenVacXin, int muiSo, LocalDate ngayTiem,
                            String soLo, String bacSi, String nguoiTiem, String trangThai, String ghiChu) {
        VaccinationRecord r = new VaccinationRecord();
        r.setKhachHang(customer);
        r.setTenVacXin(tenVacXin);
        r.setMuiSo(muiSo);
        r.setNgayTiem(ngayTiem);
        r.setSoLo(soLo);
        r.setCoSoTiem("VaxFlow Trung Tâm");
        r.setBacSiKham(bacSi);
        r.setNguoiTiem(nguoiTiem);
        r.setTrangThai(trangThai);
        r.setPhanUngSauTiem("Không có phản ứng bất thường trong 30 phút theo dõi");
        r.setGhiChu(ghiChu);
        vaccinationRecordRepository.save(r);
    }
}
