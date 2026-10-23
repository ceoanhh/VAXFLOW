package com.vaxflow.core.controller;

import com.vaxflow.core.entity.Admin;
import com.vaxflow.core.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.Duration;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final AdminService adminService;

    public ProfileController(AdminService adminService) {
        this.adminService = adminService;
    }

    private String getClientIp(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null || xfHeader.isEmpty() || "unknown".equalsIgnoreCase(xfHeader)) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0].trim();
    }

    @GetMapping
    public String profilePage(Authentication authentication, Model model) {
        if (authentication == null) {
            return "redirect:/login";
        }

        Admin user = adminService.findByTenDangNhap(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thông tin tài khoản"));

        model.addAttribute("user", user);

        // Kiem tra doi mat khau dinh ky (vi du: qua 60 ngay)
        boolean canDoiMatKhauDinhKy = false;
        long soNgayChuaDoiMatKhau = 0;
        if (user.getNgayDoiMatKhau() != null) {
            soNgayChuaDoiMatKhau = Duration.between(user.getNgayDoiMatKhau(), LocalDateTime.now()).toDays();
            if (soNgayChuaDoiMatKhau >= 60) {
                canDoiMatKhauDinhKy = true;
            }
        } else {
            canDoiMatKhauDinhKy = true;
        }

        model.addAttribute("canDoiMatKhauDinhKy", canDoiMatKhauDinhKy);
        model.addAttribute("soNgayChuaDoiMatKhau", soNgayChuaDoiMatKhau);

        return "profile";
    }

    @PostMapping("/update-phone")
    public String updatePhone(@RequestParam("soDienThoai") String soDienThoai,
                              Authentication authentication,
                              HttpServletRequest request,
                              RedirectAttributes redirectAttributes) {
        if (authentication == null) {
            return "redirect:/login";
        }
        try {
            Admin user = adminService.findByTenDangNhap(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

            adminService.updatePhone(user.getId(), soDienThoai, authentication.getName(), getClientIp(request));
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật số điện thoại thành công!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/profile";
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam("oldPassword") String oldPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 Authentication authentication,
                                 HttpServletRequest request,
                                 RedirectAttributes redirectAttributes) {
        if (authentication == null) {
            return "redirect:/login";
        }
        try {
            Admin user = adminService.findByTenDangNhap(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

            adminService.changePassword(user.getId(), oldPassword, newPassword, confirmPassword,
                    authentication.getName(), getClientIp(request));
            redirectAttributes.addFlashAttribute("successMessage", "Đổi mật khẩu thành công! Hãy ghi nhớ mật khẩu mới của bạn.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/profile";
    }
}
