package com.vaxflow.core.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;

/**
 * Controller phu trach trang Dashboard tong quan he thong
 * Route prefix: /dashboard
 * Chuc nang: Tong khach hang, tong nhan vien, doanh thu thang, canh bao ton kho thap
 */
@Controller
public class DashboardController {

    private final com.vaxflow.core.service.CustomerService customerService;
    private final com.vaxflow.employee.service.EmployeeService employeeService;
    private final com.vaxflow.invoice.service.InvoiceService invoiceService;
    private final com.vaxflow.vaccine.service.VaccineService vaccineService;

    public DashboardController(com.vaxflow.core.service.CustomerService customerService,
                               com.vaxflow.employee.service.EmployeeService employeeService,
                               com.vaxflow.invoice.service.InvoiceService invoiceService,
                               com.vaxflow.vaccine.service.VaccineService vaccineService) {
        this.customerService = customerService;
        this.employeeService = employeeService;
        this.invoiceService = invoiceService;
        this.vaccineService = vaccineService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) {
        String username = (authentication != null) ? authentication.getName() : "Admin";
        model.addAttribute("username", username);

        // Du lieu thong ke tong quan tich hop tu ca 4 phan he
        long tongKhachHang = customerService.count();
        long tongNhanVien = employeeService.getAll().size();
        BigDecimal doanhThuThang = invoiceService.findAllInvoices().stream()
                .filter(inv -> inv.getTrangThai() != null && "DA_THANH_TOAN".equals(inv.getTrangThai().name()))
                .map(com.vaxflow.invoice.entity.HoaDonKhachHang::getTongTien)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        long canhBaoTonKho = vaccineService.countLowStock();

        model.addAttribute("tongKhachHang", tongKhachHang);
        model.addAttribute("tongNhanVien", tongNhanVien);
        model.addAttribute("doanhThuThang", doanhThuThang);
        model.addAttribute("canhBaoTonKho", canhBaoTonKho);

        return "dashboard";
    }
}
