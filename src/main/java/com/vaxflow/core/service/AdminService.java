package com.vaxflow.core.service;

import com.vaxflow.core.entity.Admin;
import com.vaxflow.core.entity.Role;
import com.vaxflow.core.repository.AdminRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuditLogService auditLogService;
    private final EmailService emailService;

    public AdminService(AdminRepository adminRepository,
                        PasswordEncoder passwordEncoder,
                        AuditLogService auditLogService,
                        EmailService emailService) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
        this.auditLogService = auditLogService;
        this.emailService = emailService;
    }

    @Transactional(readOnly = true)
    public List<Admin> findAll() {
        return adminRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Admin> findById(Long id) {
        return adminRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Admin> findByTenDangNhap(String tenDangNhap) {
        return adminRepository.findByTenDangNhap(tenDangNhap);
    }

    @Transactional(readOnly = true)
    public Optional<Admin> findByEmail(String email) {
        return adminRepository.findByEmail(email);
    }

    /**
     * Admin cap tai khoan moi cho chuc danh y te tuong ung
     */
    @Transactional
    public void createAccount(String tenDangNhap, String matKhau, String hoTen,
                              Role vaiTro, String soDienThoai, String email,
                              Long idNhanVien, String performedBy, String ip) {

        if (tenDangNhap == null || tenDangNhap.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên đăng nhập không được để trống!");
        }
        tenDangNhap = tenDangNhap.trim();

        if (adminRepository.existsByTenDangNhap(tenDangNhap)) {
            throw new IllegalArgumentException("Tên đăng nhập '" + tenDangNhap + "' đã tồn tại trong hệ thống!");
        }

        if (soDienThoai != null && !soDienThoai.trim().isEmpty()) {
            soDienThoai = soDienThoai.trim();
            if (adminRepository.existsBySoDienThoai(soDienThoai)) {
                throw new IllegalArgumentException("Số điện thoại '" + soDienThoai + "' đã được sử dụng bởi tài khoản khác!");
            }
        }

        if (email != null && !email.trim().isEmpty()) {
            email = email.trim();
            if (adminRepository.existsByEmail(email)) {
                throw new IllegalArgumentException("Địa chỉ email '" + email + "' đã được đăng ký bởi tài khoản khác!");
            }
        } else {
            email = null;
        }

        if (idNhanVien != null && adminRepository.existsByIdNhanVien(idNhanVien)) {
            throw new IllegalArgumentException("Nhân viên này đã được cấp tài khoản trên hệ thống!");
        }

        if (matKhau == null || matKhau.trim().length() < 6) {
            throw new IllegalArgumentException("Mật khẩu khởi tạo phải có tối thiểu 6 ký tự!");
        }

        Admin admin = new Admin();
        admin.setTenDangNhap(tenDangNhap);
        admin.setMatKhau(passwordEncoder.encode(matKhau.trim()));
        admin.setMatKhauHienThi(matKhau.trim()); // Luu de Admin co the xem chi tiet
        admin.setHoTen(hoTen != null ? hoTen.trim() : tenDangNhap);
        admin.setVaiTro(vaiTro != null ? vaiTro : Role.ROLE_STAFF);
        admin.setSoDienThoai(soDienThoai);
        admin.setEmail(email);
        admin.setIdNhanVien(idNhanVien);
        admin.setTrangThai(true);
        admin.setAccountLocked(false);
        admin.setFailedAttempts(0);
        admin.setNgayDoiMatKhau(LocalDateTime.now());

        adminRepository.save(admin);

        auditLogService.log(performedBy, "CAP_TAI_KHOAN",
                "Cấp tài khoản mới: " + tenDangNhap + " với vai trò " + admin.getVaiTro().getMoTa(), ip);
    }

    /**
     * Cap nhat so dien thoai (Ho so ca nhan)
     */
    @Transactional
    public void updatePhone(Long id, String soDienThoai, String performedBy, String ip) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy tài khoản"));

        if (soDienThoai != null && !soDienThoai.trim().isEmpty()) {
            soDienThoai = soDienThoai.trim();
            if (adminRepository.existsBySoDienThoaiAndIdNot(soDienThoai, id)) {
                throw new IllegalArgumentException("Số điện thoại '" + soDienThoai + "' đã tồn tại trên hệ thống!");
            }
        }

        admin.setSoDienThoai(soDienThoai);
        adminRepository.save(admin);

        auditLogService.log(performedBy, "CAP_NHAT_HO_SO",
                "Cập nhật số điện thoại thành công cho tài khoản " + admin.getTenDangNhap(), ip);
    }

    /**
     * Doi mat khau nguoi dung
     */
    @Transactional
    public void changePassword(Long id, String oldPassword, String newPassword,
                               String confirmPassword, String performedBy, String ip) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy tài khoản"));

        if (!passwordEncoder.matches(oldPassword, admin.getMatKhau())) {
            throw new IllegalArgumentException("Mật khẩu hiện tại không chính xác!");
        }

        if (newPassword == null || newPassword.trim().length() < 6) {
            throw new IllegalArgumentException("Mật khẩu mới phải có tối thiểu 6 ký tự!");
        }

        if (!newPassword.equals(confirmPassword)) {
            throw new IllegalArgumentException("Mật khẩu xác nhận không khớp!");
        }

        if (passwordEncoder.matches(newPassword, admin.getMatKhau())) {
            throw new IllegalArgumentException("Mật khẩu mới không được trùng với mật khẩu cũ!");
        }

        admin.setMatKhau(passwordEncoder.encode(newPassword.trim()));
        admin.setMatKhauHienThi(newPassword.trim());
        admin.setNgayDoiMatKhau(LocalDateTime.now());
        adminRepository.save(admin);

        auditLogService.log(performedBy, "DOI_MAT_KHAU",
                "Đổi mật khẩu thành công cho tài khoản " + admin.getTenDangNhap(), ip);
    }

    /**
     * Admin Mo khoa hoac Khoa tai khoan
     */
    @Transactional
    public void toggleLock(Long id, boolean lock, String performedBy, String ip) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy tài khoản"));

        admin.setAccountLocked(lock);
        if (!lock) {
            admin.setFailedAttempts(0);
            admin.setLockTime(null);
        } else {
            admin.setLockTime(LocalDateTime.now());
        }
        adminRepository.save(admin);

        String hanhDong = lock ? "KHOA_TAI_KHOAN" : "MO_KHOA_TAI_KHOAN";
        String chiTiet = (lock ? "Khóa" : "Mở khóa") + " tài khoản " + admin.getTenDangNhap();
        auditLogService.log(performedBy, hanhDong, chiTiet, ip);
    }

    /**
     * Admin Dat lai mat khau cho tai khoan nhan vien
     */
    @Transactional
    public void resetPassword(Long id, String newPassword, String performedBy, String ip) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy tài khoản"));

        if (newPassword == null || newPassword.trim().length() < 6) {
            throw new IllegalArgumentException("Mật khẩu mới phải có tối thiểu 6 ký tự!");
        }

        admin.setMatKhau(passwordEncoder.encode(newPassword.trim()));
        admin.setMatKhauHienThi(newPassword.trim());
        admin.setFailedAttempts(0);
        admin.setAccountLocked(false);
        admin.setNgayDoiMatKhau(LocalDateTime.now());
        adminRepository.save(admin);

        auditLogService.log(performedBy, "DAT_LAI_MAT_KHAU",
                "Quản trị viên đã đặt lại mật khẩu cho tài khoản " + admin.getTenDangNhap(), ip);
    }

    /**
     * Gui ma OTP khoi phuc mat khau qua Email
     */
    @Transactional
    public String sendForgotPasswordOtp(String emailOrUsername, String ip) {
        if (emailOrUsername == null || emailOrUsername.trim().isEmpty()) {
            throw new IllegalArgumentException("Vui lòng nhập Email hoặc Tên đăng nhập!");
        }

        String search = emailOrUsername.trim();
        Admin admin = adminRepository.findByEmail(search)
                .or(() -> adminRepository.findByTenDangNhap(search))
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy tài khoản tương ứng với thông tin đã nhập!"));

        if (admin.getEmail() == null || admin.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Tài khoản '" + admin.getTenDangNhap() + "' chưa cập nhật địa chỉ Email! Vui lòng liên hệ Quản trị viên để được đặt lại mật khẩu.");
        }

        String otp = emailService.generateOtp();
        admin.setResetToken(otp);
        admin.setResetTokenExpiry(LocalDateTime.now().plusMinutes(15));
        adminRepository.save(admin);

        emailService.sendPasswordResetOtp(admin.getEmail(), admin.getHoTen(), otp);
        auditLogService.log(admin.getTenDangNhap(), "YEU_CAU_KHOI_PHUC_MAT_KHAU",
                "Yêu cầu mã OTP khôi phục mật khẩu gửi đến email: " + admin.getEmail(), ip);

        return admin.getEmail();
    }

    /**
     * Xac nhan ma OTP va dat lai mat khau moi
     */
    @Transactional
    public void resetPasswordWithOtp(String email, String otp, String newPassword, String confirmPassword, String ip) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Địa chỉ email không hợp lệ!");
        }

        Admin admin = adminRepository.findByEmail(email.trim())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy tài khoản với email này!"));

        if (otp == null || admin.getResetToken() == null || !admin.getResetToken().equals(otp.trim())) {
            throw new IllegalArgumentException("Mã xác thực OTP không chính xác!");
        }

        if (admin.getResetTokenExpiry() == null || admin.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Mã xác thực OTP đã hết hạn (chỉ có hiệu lực trong 15 phút)! Vui lòng yêu cầu mã mới.");
        }

        if (newPassword == null || newPassword.trim().length() < 6) {
            throw new IllegalArgumentException("Mật khẩu mới phải có tối thiểu 6 ký tự!");
        }

        if (!newPassword.equals(confirmPassword)) {
            throw new IllegalArgumentException("Mật khẩu xác nhận không khớp!");
        }

        admin.setMatKhau(passwordEncoder.encode(newPassword.trim()));
        admin.setMatKhauHienThi(newPassword.trim());
        admin.setResetToken(null);
        admin.setResetTokenExpiry(null);
        admin.setFailedAttempts(0);
        admin.setAccountLocked(false);
        admin.setLockTime(null);
        admin.setNgayDoiMatKhau(LocalDateTime.now());
        adminRepository.save(admin);

        auditLogService.log(admin.getTenDangNhap(), "KHOI_PHUC_MAT_KHAU_THANH_CONG",
                "Khôi phục mật khẩu thành công qua mã OTP Email", ip);
    }
}
