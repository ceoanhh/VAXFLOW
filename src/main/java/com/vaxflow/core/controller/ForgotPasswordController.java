package com.vaxflow.core.controller;

import com.vaxflow.core.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
public class ForgotPasswordController {

    private final AdminService adminService;

    public ForgotPasswordController(AdminService adminService) {
        this.adminService = adminService;
    }

    private String getClientIp(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null || xfHeader.isEmpty() || "unknown".equalsIgnoreCase(xfHeader)) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0].trim();
    }

    /**
     * Trang yeu cau quen mat khau
     */
    @GetMapping("/forgot-password")
    public String forgotPasswordPage() {
        return "forgot_password";
    }

    /**
     * Xu ly gui ma OTP ve Email
     */
    @PostMapping("/forgot-password")
    public String handleForgotPassword(@RequestParam("emailOrUsername") String emailOrUsername,
                                       HttpServletRequest request,
                                       RedirectAttributes redirectAttributes) {
        try {
            String email = adminService.sendForgotPasswordOtp(emailOrUsername, getClientIp(request));
            redirectAttributes.addFlashAttribute("successMessage",
                    "Mã xác thực OTP (6 chữ số) đã được gửi đến hòm thư: " + email + ". Vui lòng kiểm tra hộp thư để lấy mã!");
            return "redirect:/reset-password?email=" + URLEncoder.encode(email, StandardCharsets.UTF_8);
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/forgot-password";
        }
    }

    /**
     * Trang nhap ma OTP va mat khau moi
     */
    @GetMapping("/reset-password")
    public String resetPasswordPage(@RequestParam(value = "email", required = false) String email,
                                    Model model) {
        model.addAttribute("email", email);
        return "reset_password";
    }

    /**
     * Xu ly xac thuc OTP va cap nhat mat khau moi
     */
    @PostMapping("/reset-password")
    public String handleResetPassword(@RequestParam("email") String email,
                                      @RequestParam("otp") String otp,
                                      @RequestParam("newPassword") String newPassword,
                                      @RequestParam("confirmPassword") String confirmPassword,
                                      HttpServletRequest request,
                                      RedirectAttributes redirectAttributes) {
        try {
            adminService.resetPasswordWithOtp(email, otp, newPassword, confirmPassword, getClientIp(request));
            redirectAttributes.addFlashAttribute("logoutMessage",
                    "Khôi phục mật khẩu thành công! Bạn có thể đăng nhập ngay với mật khẩu mới.");
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/reset-password?email=" + URLEncoder.encode(email, StandardCharsets.UTF_8);
        }
    }
}
