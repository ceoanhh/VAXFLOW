package com.vaxflow.core.config;

import com.vaxflow.core.entity.Admin;
import com.vaxflow.core.repository.AdminRepository;
import com.vaxflow.core.service.AuditLogService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class CustomAuthHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler {

    private final AdminRepository adminRepository;
    private final AuditLogService auditLogService;

    public CustomAuthHandler(AdminRepository adminRepository, AuditLogService auditLogService) {
        this.adminRepository = adminRepository;
        this.auditLogService = auditLogService;
    }

    private String getClientIp(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null || xfHeader.isEmpty() || "unknown".equalsIgnoreCase(xfHeader)) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0].trim();
    }

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String username = authentication.getName();
        String ip = getClientIp(request);

        adminRepository.findByTenDangNhap(username).ifPresent(admin -> {
            admin.setFailedAttempts(0);
            admin.setLastLoginAt(LocalDateTime.now());
            adminRepository.save(admin);
        });

        auditLogService.log(username, "DANG_NHAP_THANH_CONG", "Đăng nhập thành công vào hệ thống", ip);

        response.sendRedirect(request.getContextPath() + "/dashboard");
    }

    @Override
    @Transactional
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        String username = request.getParameter("username");
        String ip = getClientIp(request);

        if (exception instanceof LockedException) {
            auditLogService.log(username, "DANG_NHAP_THAT_BAI", "Cố gắng đăng nhập vào tài khoản đang bị khóa", ip);
            response.sendRedirect(request.getContextPath() + "/login?locked=true");
            return;
        }

        if (exception instanceof DisabledException) {
            auditLogService.log(username, "DANG_NHAP_THAT_BAI", "Cố gắng đăng nhập vào tài khoản đã bị vô hiệu hóa", ip);
            response.sendRedirect(request.getContextPath() + "/login?disabled=true");
            return;
        }

        if (username != null && !username.trim().isEmpty()) {
            Optional<Admin> optionalAdmin = adminRepository.findByTenDangNhap(username.trim());
            if (optionalAdmin.isPresent()) {
                Admin admin = optionalAdmin.get();
                if (admin.isAccountLocked()) {
                    response.sendRedirect(request.getContextPath() + "/login?locked=true");
                    return;
                }

                int attempts = admin.getFailedAttempts() + 1;
                admin.setFailedAttempts(attempts);

                if (attempts >= 5) {
                    admin.setAccountLocked(true);
                    admin.setLockTime(LocalDateTime.now());
                    adminRepository.save(admin);
                    auditLogService.log(username, "KHOA_TAI_KHOAN",
                            "Tài khoản bị khóa do nhập sai mật khẩu 5 lần liên tiếp. Cảnh báo gửi tới Quản trị viên!", ip);
                    response.sendRedirect(request.getContextPath() + "/login?locked=true");
                    return;
                } else {
                    adminRepository.save(admin);
                    auditLogService.log(username, "DANG_NHAP_THAT_BAI",
                            "Nhập sai mật khẩu lần " + attempts + "/5", ip);
                    response.sendRedirect(request.getContextPath() + "/login?error=true&attempts=" + attempts);
                    return;
                }
            }
        }

        auditLogService.log(username != null ? username : "N/A", "DANG_NHAP_THAT_BAI",
                "Tài khoản hoặc mật khẩu không tồn tại", ip);
        response.sendRedirect(request.getContextPath() + "/login?error=true");
    }
}
