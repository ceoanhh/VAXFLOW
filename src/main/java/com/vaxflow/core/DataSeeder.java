package com.vaxflow.core;

import com.vaxflow.core.entity.Admin;
import com.vaxflow.core.entity.HoSo;
import com.vaxflow.core.entity.KhachHang;
import com.vaxflow.core.entity.LichSuTiem;
import com.vaxflow.core.repository.AdminRepository;
import com.vaxflow.core.repository.HoSoRepository;
import com.vaxflow.core.repository.KhachHangRepository;
import com.vaxflow.core.repository.LichSuTiemRepository;
import com.vaxflow.employee.entity.Department;
import com.vaxflow.employee.entity.Employee;
import com.vaxflow.employee.entity.Gender;
import com.vaxflow.employee.entity.Position;
import com.vaxflow.employee.repository.DepartmentRepository;
import com.vaxflow.employee.repository.EmployeeRepository;
import com.vaxflow.invoice.entity.HoaDonChiTiet;
import com.vaxflow.invoice.entity.HoaDonKhachHang;
import com.vaxflow.invoice.entity.PhuongThucThanhToan;
import com.vaxflow.invoice.entity.ThanhToan;
import com.vaxflow.invoice.entity.TrangThaiHoaDon;
import com.vaxflow.invoice.repository.HoaDonChiTietRepository;
import com.vaxflow.invoice.repository.HoaDonKhachHangRepository;
import com.vaxflow.invoice.repository.ThanhToanRepository;
import com.vaxflow.vaccine.entity.LoVacXin;
import com.vaxflow.vaccine.entity.VacXin;
import com.vaxflow.vaccine.repository.LoVacXinRepository;
import com.vaxflow.vaccine.repository.VacXinRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DataSeeder dung chung cho toan bo 5 thanh vien trong team VaxFlow.
 * Chi chay khi Spring profile la 'dev'.
 */
@Component
@Profile("dev")
public class DataSeeder implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    // Phan he Khach hang & Tiem chung
    private final KhachHangRepository khachHangRepository;
    private final LichSuTiemRepository lichSuTiemRepository;
    private final HoSoRepository hoSoRepository;

    // Phan he Hoa don & Thanh toan
    private final HoaDonKhachHangRepository hoaDonKhachHangRepository;
    private final HoaDonChiTietRepository hoaDonChiTietRepository;
    private final ThanhToanRepository thanhToanRepository;

    // Phan he Nhan su & Phong ban
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    // Phan he Vac xin & Kho duoc GSP
    private final VacXinRepository vacXinRepository;
    private final LoVacXinRepository loVacXinRepository;

    public DataSeeder(AdminRepository adminRepository,
                      PasswordEncoder passwordEncoder,
                      KhachHangRepository khachHangRepository,
                      LichSuTiemRepository lichSuTiemRepository,
                      HoSoRepository hoSoRepository,
                      HoaDonKhachHangRepository hoaDonKhachHangRepository,
                      HoaDonChiTietRepository hoaDonChiTietRepository,
                      ThanhToanRepository thanhToanRepository,
                      DepartmentRepository departmentRepository,
                      EmployeeRepository employeeRepository,
                      VacXinRepository vacXinRepository,
                      LoVacXinRepository loVacXinRepository) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
        this.khachHangRepository = khachHangRepository;
        this.lichSuTiemRepository = lichSuTiemRepository;
        this.hoSoRepository = hoSoRepository;
        this.hoaDonKhachHangRepository = hoaDonKhachHangRepository;
        this.hoaDonChiTietRepository = hoaDonChiTietRepository;
        this.thanhToanRepository = thanhToanRepository;
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
        this.vacXinRepository = vacXinRepository;
        this.loVacXinRepository = loVacXinRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info(">>> [VAXFLOW] Dang khoi chay DataSeeder cho toan bo 5 phan he...");

        seedAdmin();
        seedDepartmentsAndEmployees();
        seedVaccinesAndBatches();
        seedCustomersAndVaccinationRecords();
        seedInvoicesAndPayments();

        log.info(">>> [VAXFLOW] Hoan tat khoi tao du lieu mau toan he thong!");
    }

    /**
     * Khoi tao tai khoan quan tri vien he thong
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
            log.info(">>> [He Thong] Da tao tai khoan Admin: admin / admin123");
        }
    }

    /**
     * Khoi tao danh muc phong ban va nhan su y te mau
     */
    private void seedDepartmentsAndEmployees() {
        if (departmentRepository.count() == 0) {
            Department d1 = new Department();
            d1.setTen("Phòng Khám & Tiêm Chủng");
            d1.setGhiChu("Khám sàng lọc trước tiêm và thực hiện tiêm chủng");
            departmentRepository.save(d1);

            Department d2 = new Department();
            d2.setTen("Phòng Dược & Kho Vắc Xin");
            d2.setGhiChu("Bảo quản dây chuyền lạnh GSP");
            departmentRepository.save(d2);

            Department d3 = new Department();
            d3.setTen("Phòng Quản Lý & Điều Hành");
            d3.setGhiChu("Ban giám đốc và điều phối trung tâm");
            departmentRepository.save(d3);

            Department d4 = new Department();
            d4.setTen("Phòng Kế Toán & Thu Ngân");
            d4.setGhiChu("Thu ngân và xuất hóa đơn");
            departmentRepository.save(d4);

            if (employeeRepository.count() == 0) {
                Employee e1 = new Employee();
                e1.setMaNhanSu("NV-001");
                e1.setHoVaTen("BS. Nguyễn Văn An");
                e1.setCccd("001200001234");
                e1.setNgaySinh(LocalDate.of(1985, 5, 20));
                e1.setGioiTinh(Gender.MALE);
                e1.setDiaChi("123 Hai Bà Trưng, Quận 1, TP. HCM");
                e1.setSdt("0901234567");
                e1.setEmail("vanan.nguyen@vaxflow.vn");
                e1.setChucVu(Position.DOCTOR);
                e1.setNgayVaoLam(LocalDate.of(2021, 1, 15));
                e1.setLuongCung(new BigDecimal("25000000"));
                e1.setPhongBan(d1);
                employeeRepository.save(e1);

                Employee e2 = new Employee();
                e2.setMaNhanSu("NV-002");
                e2.setHoVaTen("ĐD. Trần Thị Mai");
                e2.setCccd("001200005678");
                e2.setNgaySinh(LocalDate.of(1992, 8, 12));
                e2.setGioiTinh(Gender.FEMALE);
                e2.setDiaChi("45 Lê Duẩn, Quận Hải Châu, Đà Nẵng");
                e2.setSdt("0912345678");
                e2.setEmail("thimai.tran@vaxflow.vn");
                e2.setChucVu(Position.NURSE);
                e2.setNgayVaoLam(LocalDate.of(2022, 3, 1));
                e2.setLuongCung(new BigDecimal("15000000"));
                e2.setPhongBan(d1);
                employeeRepository.save(e2);

                Employee e3 = new Employee();
                e3.setMaNhanSu("NV-003");
                e3.setHoVaTen("DS. Lê Hoàng Nam");
                e3.setCccd("001200009999");
                e3.setNgaySinh(LocalDate.of(1990, 11, 25));
                e3.setGioiTinh(Gender.MALE);
                e3.setDiaChi("78 Trần Hưng Đạo, Hà Nội");
                e3.setSdt("0987654321");
                e3.setEmail("hoangnam.le@vaxflow.vn");
                e3.setChucVu(Position.STAFF);
                e3.setNgayVaoLam(LocalDate.of(2021, 6, 10));
                e3.setLuongCung(new BigDecimal("18000000"));
                e3.setPhongBan(d2);
                employeeRepository.save(e3);

                Employee e4 = new Employee();
                e4.setMaNhanSu("NV-004");
                e4.setHoVaTen("ThS. Phạm Thu Trang");
                e4.setCccd("001200008888");
                e4.setNgaySinh(LocalDate.of(1988, 3, 14));
                e4.setGioiTinh(Gender.FEMALE);
                e4.setDiaChi("12 Nguyễn Huệ, Quận 1, TP. HCM");
                e4.setSdt("0934567890");
                e4.setEmail("thutrang.pham@vaxflow.vn");
                e4.setChucVu(Position.MANAGER);
                e4.setNgayVaoLam(LocalDate.of(2020, 10, 1));
                e4.setLuongCung(new BigDecimal("30000000"));
                e4.setPhongBan(d3);
                employeeRepository.save(e4);

                log.info(">>> [Nhan Su] Da khoi tao 4 phong ban va 4 nhan su y te mau");
            }
        }
    }

    /**
     * Khoi tao danh muc vac xin va cac lo hang kho duoc GSP
     */
    private void seedVaccinesAndBatches() {
        if (vacXinRepository.count() == 0) {
            VacXin v1 = new VacXin();
            v1.setMaVacXin("VX-001");
            v1.setTenVacXin("Hexaxim 6 trong 1");
            v1.setNhaSanXuat("Sanofi Pasteur (Pháp)");
            v1.setLoaiBenhPhong("Bạch hầu, ho gà, uốn ván, bại liệt, Hib, viêm gan B");
            v1.setSoMuiCanTiem(3);
            v1.setKhoangCachNgay(30);
            v1.setDonGia(new BigDecimal("1050000"));
            vacXinRepository.save(v1);

            VacXin v2 = new VacXin();
            v2.setMaVacXin("VX-002");
            v2.setTenVacXin("Prevenar 13 (Phế cầu)");
            v2.setNhaSanXuat("Pfizer (Mỹ)");
            v2.setLoaiBenhPhong("Viêm phổi, viêm tai giữa, viêm màng não do phế cầu khuẩn");
            v2.setSoMuiCanTiem(4);
            v2.setKhoangCachNgay(60);
            v2.setDonGia(new BigDecimal("1290000"));
            vacXinRepository.save(v2);

            VacXin v3 = new VacXin();
            v3.setMaVacXin("VX-003");
            v3.setTenVacXin("Gardasil 9 (HPV)");
            v3.setNhaSanXuat("MSD (Mỹ)");
            v3.setLoaiBenhPhong("Ung thư cổ tử cung và các bệnh do virus HPV");
            v3.setSoMuiCanTiem(3);
            v3.setKhoangCachNgay(60);
            v3.setDonGia(new BigDecimal("2950000"));
            vacXinRepository.save(v3);

            if (loVacXinRepository.count() == 0) {
                LoVacXin l1 = new LoVacXin();
                l1.setVacXin(v1);
                l1.setMaLo("LO-HEXA-2026A");
                l1.setNgayNhap(LocalDateTime.now().minusDays(10));
                l1.setHanSuDung(LocalDate.of(2027, 12, 31));
                l1.setSoLuongNhap(200);
                l1.setSoLuongConLai(185);
                loVacXinRepository.save(l1);

                LoVacXin l2 = new LoVacXin();
                l2.setVacXin(v2);
                l2.setMaLo("LO-PREV-2026B");
                l2.setNgayNhap(LocalDateTime.now().minusDays(5));
                l2.setHanSuDung(LocalDate.of(2027, 8, 15));
                l2.setSoLuongNhap(150);
                l2.setSoLuongConLai(140);
                loVacXinRepository.save(l2);

                LoVacXin l3 = new LoVacXin();
                l3.setVacXin(v3);
                l3.setMaLo("LO-GARD-2026C");
                l3.setNgayNhap(LocalDateTime.now().minusDays(20));
                l3.setHanSuDung(LocalDate.of(2026, 12, 31));
                l3.setSoLuongNhap(50);
                l3.setSoLuongConLai(8); // Tồn kho thấp <= 10 để test cảnh báo
                loVacXinRepository.save(l3);

                log.info(">>> [Kho Duoc] Da khoi tao 3 loai vac xin va 3 lo hang kho GSP");
            }
        }
    }

    /**
     * Khoi tao ho so khach hang va lich su tiem chung
     */
    private void seedCustomersAndVaccinationRecords() {
        if (khachHangRepository.count() == 0) {
            KhachHang k1 = new KhachHang();
            k1.setMaKhachHang("KH-001");
            k1.setTenKhachHang("Bé Trần Gia Bảo");
            k1.setNgaySinh(LocalDate.of(2025, 6, 15));
            k1.setSdt("0988776655");
            k1.setDiaChi("45 Nguyễn Trãi, Quận 5, TP. HCM");
            khachHangRepository.save(k1);

            KhachHang k2 = new KhachHang();
            k2.setMaKhachHang("KH-002");
            k2.setTenKhachHang("Nguyễn Minh Anh");
            k2.setNgaySinh(LocalDate.of(2002, 11, 20));
            k2.setSdt("0977112233");
            k2.setDiaChi("120 Cầu Giấy, Hà Nội");
            khachHangRepository.save(k2);

            KhachHang k3 = new KhachHang();
            k3.setMaKhachHang("KH-003");
            k3.setTenKhachHang("Lê Bảo Hân");
            k3.setNgaySinh(LocalDate.of(2024, 1, 10));
            k3.setSdt("0918273645");
            k3.setDiaChi("89 Hoàng Diệu, Đà Nẵng");
            khachHangRepository.save(k3);

            if (lichSuTiemRepository.count() == 0) {
                LichSuTiem lst1 = new LichSuTiem();
                lst1.setMaTiem("TIEM-2026-001");
                lst1.setIdVaccine(1L);
                lst1.setIdLoVaccine(1L);
                lst1.setSoMuiTiem(1);
                lst1.setNgayTiem(LocalDate.now().minusDays(2));
                lst1.setTrangThai("HOAN_THANH");
                lst1.getKhachHangSet().add(k1);
                k1.getLichSuTiemSet().add(lst1);
                lichSuTiemRepository.save(lst1);
                khachHangRepository.save(k1);

                HoSo hs1 = new HoSo();
                hs1.setMaHoSo("HS-2026-001");
                hs1.setIdBacSiKham(1L);
                hs1.setIdBacSiTiem(1L);
                hs1.setIdDieuDuong(2L);
                hs1.setIdTiepDon(4L);
                hs1.setGhiChuTheoDoi("Trẻ khỏe mạnh, không sốt, phản ứng bình thường sau tiêm.");
                hs1.setLichSuTiem(lst1);
                hoSoRepository.save(hs1);

                LichSuTiem lst2 = new LichSuTiem();
                lst2.setMaTiem("TIEM-2026-002");
                lst2.setIdVaccine(2L);
                lst2.setIdLoVaccine(2L);
                lst2.setSoMuiTiem(1);
                lst2.setNgayTiem(LocalDate.now().minusDays(1));
                lst2.setTrangThai("HOAN_THANH");
                lst2.getKhachHangSet().add(k2);
                k2.getLichSuTiemSet().add(lst2);
                lichSuTiemRepository.save(lst2);
                khachHangRepository.save(k2);

                HoSo hs2 = new HoSo();
                hs2.setMaHoSo("HS-2026-002");
                hs2.setIdBacSiKham(1L);
                hs2.setIdBacSiTiem(1L);
                hs2.setIdDieuDuong(2L);
                hs2.setIdTiepDon(4L);
                hs2.setGhiChuTheoDoi("Khám sàng lọc đủ điều kiện tiêm chủng.");
                hs2.setLichSuTiem(lst2);
                hoSoRepository.save(hs2);

                log.info(">>> [Tiem Chung] Da khoi tao 3 khach hang, 2 lich su tiem va 2 ho so");
            }
        }
    }

    /**
     * Khoi tao hoa don thu phi va giao dich thanh toan mau
     */
    private void seedInvoicesAndPayments() {
        if (hoaDonKhachHangRepository.count() == 0) {
            HoaDonKhachHang hd1 = new HoaDonKhachHang();
            hd1.setMaHoaDon("HD-2026-001");
            hd1.setNgayLap(LocalDateTime.now().minusDays(2));
            hd1.setIdHoSo(1L);
            hd1.setTongTien(new BigDecimal("1050000"));
            hd1.setTrangThai(TrangThaiHoaDon.DA_THANH_TOAN);
            hd1.setGhiChu("Thanh toán tiền mặt tại quầy thu ngân");
            hoaDonKhachHangRepository.save(hd1);

            HoaDonChiTiet ct1 = new HoaDonChiTiet();
            ct1.setIdHoaDon(hd1.getId());
            ct1.setIdTiem(1L);
            ct1.setSoLuong(1);
            ct1.setDonGia(new BigDecimal("1050000"));
            ct1.setThanhTien(new BigDecimal("1050000"));
            hoaDonChiTietRepository.save(ct1);

            ThanhToan tt1 = new ThanhToan();
            tt1.setMaThanhToan("TT-2026-001");
            tt1.setIdHoaDon(hd1.getId());
            tt1.setIdThuNgan(4L);
            tt1.setSoTien(new BigDecimal("1050000"));
            tt1.setPhuongThucTt(PhuongThucThanhToan.TIEN_MAT);
            tt1.setNgayThu(LocalDateTime.now().minusDays(2));
            tt1.setGhiChu("Đã thu đủ 1,050,000 đ");
            thanhToanRepository.save(tt1);

            HoaDonKhachHang hd2 = new HoaDonKhachHang();
            hd2.setMaHoaDon("HD-2026-002");
            hd2.setNgayLap(LocalDateTime.now().minusDays(1));
            hd2.setIdHoSo(2L);
            hd2.setTongTien(new BigDecimal("1290000"));
            hd2.setTrangThai(TrangThaiHoaDon.CHUA_THANH_TOAN);
            hd2.setGhiChu("Chờ thanh toán chuyển khoản qua QR");
            hoaDonKhachHangRepository.save(hd2);

            HoaDonChiTiet ct2 = new HoaDonChiTiet();
            ct2.setIdHoaDon(hd2.getId());
            ct2.setIdTiem(2L);
            ct2.setSoLuong(1);
            ct2.setDonGia(new BigDecimal("1290000"));
            ct2.setThanhTien(new BigDecimal("1290000"));
            hoaDonChiTietRepository.save(ct2);

            log.info(">>> [Tai Chinh] Da khoi tao 2 hoa don va 1 giao dich thanh toan mau");
        }
    }
}
