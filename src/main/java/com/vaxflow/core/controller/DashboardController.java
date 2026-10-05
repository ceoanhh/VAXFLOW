package com.vaxflow.core.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;

/**
 * Controller phu trach trang Dashboard tong quan (Person 1 - Bright)
 * Route prefix: /dashboard
 * Chuc nang: Tong khach hang, tong nhan vien, doanh thu thang, canh bao ton kho thap
 */
@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) {
        // Lay thong tin quan tri vien dang dang nhap
        String username = (authentication != null) ? authentication.getName() : "Admin";
        model.addAttribute("username", username);

        // Du lieu thong ke tong quan ban dau (se duoc tich hop voi Service cua Person 2, 3, 4, 5 khi merge)
        long tongKhachHang = 0;                     // TODO: Tich hop CustomerService (Person 2 - Bao)
        long tongNhanVien = 0;                      // TODO: Tich hop EmployeeService (Person 4 - Lan Anh)
        BigDecimal doanhThuThang = BigDecimal.ZERO; // TODO: Tich hop InvoiceService (Person 3 - Khanh)
        long canhBaoTonKho = 0;                     // TODO: Tich hop InventoryService (Person 5 - Truong)

        model.addAttribute("tongKhachHang", tongKhachHang);
        model.addAttribute("tongNhanVien", tongNhanVien);
        model.addAttribute("doanhThuThang", doanhThuThang);
        model.addAttribute("canhBaoTonKho", canhBaoTonKho);

        return "dashboard";
    }
}
