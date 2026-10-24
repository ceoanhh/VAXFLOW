package com.vaxflow.core.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller phu trach xac thuc va dang nhap he thong
 * Route prefix: /login, /
 */
@Controller
public class AuthController {

    /**
     * Truy cap goc: Neu da dang nhap thi vao dashboard, chua dang nhap thi vao login
     */
    @GetMapping("/")
    public String rootRedirect() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/dashboard";
        }
        return "redirect:/login";
    }

    /**
     * Trang dang nhap he thong voi cac thong diep bao mat
     */
    @GetMapping("/login")
    public String loginPage(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "attempts", required = false) Integer attempts,
            @RequestParam(value = "locked", required = false) String locked,
            @RequestParam(value = "disabled", required = false) String disabled,
            @RequestParam(value = "logout", required = false) String logout,
            Model model
    ) {
        // Neu da dang nhap roi thi chuyen thang vao dashboard
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/dashboard";
        }

        if (locked != null) {
            model.addAttribute("lockedMessage",
                    "Tài khoản của bạn đã bị KHÓA do nhập sai mật khẩu 5 lần liên tiếp! Cảnh báo đã được gửi đến Quản trị viên. Vui lòng liên hệ Quản trị viên để mở khóa.");
        } else if (disabled != null) {
            model.addAttribute("errorMessage", "Tài khoản này đã bị vô hiệu hóa bởi Quản trị viên!");
        } else if (error != null) {
            if (attempts != null && attempts > 0) {
                model.addAttribute("errorMessage",
                        "Tên đăng nhập hoặc mật khẩu không chính xác! Đã sai " + attempts + "/5 lần. Tài khoản sẽ bị khóa nếu sai quá 5 lần.");
            } else {
                model.addAttribute("errorMessage", "Tên đăng nhập hoặc mật khẩu không chính xác!");
            }
        }

        if (logout != null) {
            model.addAttribute("logoutMessage", "Bạn đã đăng xuất thành công khỏi hệ thống!");
        }

        return "login";
    }
}
