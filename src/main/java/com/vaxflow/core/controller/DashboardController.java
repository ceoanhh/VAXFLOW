package com.vaxflow.core.controller;

import com.vaxflow.core.service.CustomerService;
import com.vaxflow.core.service.VaccinationService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.stream.Collectors;

/**
 * Controller phu trach trang Dashboard tong quan Admin
 * Route prefix: /dashboard
 */
@Controller
public class DashboardController {

    private final CustomerService customerService;
    private final VaccinationService vaccinationService;

    public DashboardController(CustomerService customerService, VaccinationService vaccinationService) {
        this.customerService = customerService;
        this.vaccinationService = vaccinationService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) {
        String username = (authentication != null) ? authentication.getName() : "Admin";
        model.addAttribute("username", username);

        // Du lieu thong ke he thong thuc te tu database
        long tongKhachHang = customerService.countCustomers();
        long tongMuiTiem = vaccinationService.countTotalDoses();
        long muiDaTiem = vaccinationService.countAdministeredDoses();
        long muiHenTiem = vaccinationService.countScheduledDoses();
        long muiTiemHomNay = vaccinationService.countTodayDoses();

        model.addAttribute("tongKhachHang", tongKhachHang);
        model.addAttribute("tongMuiTiem", tongMuiTiem);
        model.addAttribute("muiDaTiem", muiDaTiem);
        model.addAttribute("muiHenTiem", muiHenTiem);
        model.addAttribute("muiTiemHomNay", muiTiemHomNay);

        // 5 khach hang moi nhat
        model.addAttribute("recentCustomers",
                customerService.getAllCustomers().stream().limit(5).collect(Collectors.toList()));

        // 6 mui tiem moi ghi nhan nhat
        model.addAttribute("recentVaccinations",
                vaccinationService.getAllRecords().stream().limit(6).collect(Collectors.toList()));

        return "dashboard";
    }
}
