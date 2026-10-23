package com.vaxflow.core.controller;

import com.vaxflow.core.entity.Admin;
import com.vaxflow.core.entity.Role;
import com.vaxflow.core.service.AdminService;
import com.vaxflow.core.service.AuditLogService;
import com.vaxflow.employee.entity.Employee;
import com.vaxflow.employee.repository.EmployeeRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminUserController {

    private final AdminService adminService;
    private final EmployeeRepository employeeRepository;
    private final AuditLogService auditLogService;

    public AdminUserController(AdminService adminService,
                               EmployeeRepository employeeRepository,
                               AuditLogService auditLogService) {
        this.adminService = adminService;
        this.employeeRepository = employeeRepository;
        this.auditLogService = auditLogService;
    }

    private String getClientIp(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null || xfHeader.isEmpty() || "unknown".equalsIgnoreCase(xfHeader)) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0].trim();
    }

    /**
     * Danh sach tai khoan he thong
     */
    @GetMapping("/users")
    public String listUsers(Model model) {
        List<Admin> users = adminService.findAll();
        model.addAttribute("users", users);
        return "admin/users";
    }

    /**
     * Form cap tai khoan moi cho chuc danh y te
     */
    @GetMapping("/users/new")
    public String newUserForm(Model model) {
        List<Employee> employees = employeeRepository.findAll();
        model.addAttribute("employees", employees);
        model.addAttribute("roles", Role.values());
        return "admin/user_form";
    }

    /**
     * Xu ly cap tai khoan moi
     */
    @PostMapping("/users/new")
    public String createUser(@RequestParam("tenDangNhap") String tenDangNhap,
                             @RequestParam("matKhau") String matKhau,
                             @RequestParam("hoTen") String hoTen,
                             @RequestParam("vaiTro") Role vaiTro,
                             @RequestParam(value = "soDienThoai", required = false) String soDienThoai,
                             @RequestParam(value = "email", required = false) String email,
                             @RequestParam(value = "idNhanVien", required = false) Long idNhanVien,
                             Authentication authentication,
                             HttpServletRequest request,
                             RedirectAttributes redirectAttributes) {
        try {
            adminService.createAccount(tenDangNhap, matKhau, hoTen, vaiTro,
                    soDienThoai, email, idNhanVien,
                    authentication != null ? authentication.getName() : "ADMIN",
                    getClientIp(request));

            redirectAttributes.addFlashAttribute("successMessage",
                    "Cấp tài khoản '" + tenDangNhap + "' thành công cho vai trò: " + vaiTro.getMoTa());
            return "redirect:/admin/users";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/admin/users/new";
        }
    }

    /**
     * Khoa / Mo khoa tai khoan (Xu ly khi bi khoa do sai 5 lan)
     */
    @PostMapping("/users/{id}/toggle-lock")
    public String toggleLock(@PathVariable("id") Long id,
                             @RequestParam("lock") boolean lock,
                             Authentication authentication,
                             HttpServletRequest request,
                             RedirectAttributes redirectAttributes) {
        try {
            adminService.toggleLock(id, lock,
                    authentication != null ? authentication.getName() : "ADMIN",
                    getClientIp(request));

            String msg = lock ? "Đã khóa tài khoản thành công!" : "Đã mở khóa tài khoản thành công! Số lần nhập sai đã được reset.";
            redirectAttributes.addFlashAttribute("successMessage", msg);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/admin/users";
    }

    /**
     * Dat lai mat khau cho nhan vien
     */
    @PostMapping("/users/{id}/reset-password")
    public String resetPassword(@PathVariable("id") Long id,
                                @RequestParam("newPassword") String newPassword,
                                Authentication authentication,
                                HttpServletRequest request,
                                RedirectAttributes redirectAttributes) {
        try {
            adminService.resetPassword(id, newPassword,
                    authentication != null ? authentication.getName() : "ADMIN",
                    getClientIp(request));

            redirectAttributes.addFlashAttribute("successMessage", "Đặt lại mật khẩu thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/admin/users";
    }

    /**
     * Xem nhat ky hoat dong he thong (Audit Logs)
     */
    @GetMapping("/audit-logs")
    public String auditLogs(Model model) {
        model.addAttribute("logs", auditLogService.getRecentLogs());
        return "admin/audit_logs";
    }
}
