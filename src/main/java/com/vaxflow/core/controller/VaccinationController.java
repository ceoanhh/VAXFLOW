package com.vaxflow.core.controller;

import com.vaxflow.core.entity.Customer;
import com.vaxflow.core.entity.VaccinationRecord;
import com.vaxflow.core.service.CustomerService;
import com.vaxflow.core.service.VaccinationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

/**
 * Controller quan ly cac mui tiem chung / lich tiem
 * Route prefix: /vaccinations
 */
@Controller
@RequestMapping("/vaccinations")
public class VaccinationController {

    private final VaccinationService vaccinationService;
    private final CustomerService customerService;

    public VaccinationController(VaccinationService vaccinationService, CustomerService customerService) {
        this.vaccinationService = vaccinationService;
        this.customerService = customerService;
    }

    /**
     * Danh sach tat ca cac mui tiem trong he thong
     */
    @GetMapping
    public String listVaccinations(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<VaccinationRecord> records = vaccinationService.searchRecords(keyword);
        model.addAttribute("records", records);
        model.addAttribute("keyword", keyword);
        model.addAttribute("totalRecords", vaccinationService.countTotalDoses());
        model.addAttribute("administeredCount", vaccinationService.countAdministeredDoses());
        model.addAttribute("scheduledCount", vaccinationService.countScheduledDoses());
        model.addAttribute("todayCount", vaccinationService.countTodayDoses());
        return "vaccinations/list";
    }

    /**
     * Giao dien ghi nhan mui tiem moi
     */
    @GetMapping("/create")
    public String showCreateForm(@RequestParam(value = "customerId", required = false) Long customerId, Model model) {
        VaccinationRecord record = new VaccinationRecord();
        record.setNgayTiem(LocalDate.now());
        record.setTrangThai("DA_TIEM");
        record.setCoSoTiem("VaxFlow Trung Tâm 1");

        if (customerId != null) {
            customerService.getCustomerById(customerId).ifPresent(record::setKhachHang);
        }

        model.addAttribute("record", record);
        model.addAttribute("selectedCustomerId", customerId);
        model.addAttribute("customers", customerService.getAllCustomers());
        model.addAttribute("commonVaccines", VaccinationService.COMMON_VACCINES);
        model.addAttribute("pageTitle", "Ghi Nhận Mũi Tiêm Chủng Mới");
        return "vaccinations/form";
    }

    /**
     * Xu ly luu mui tiem moi
     */
    @PostMapping("/create")
    public String createRecord(@ModelAttribute("record") VaccinationRecord record,
                               @RequestParam("khachHangId") Long khachHangId,
                               RedirectAttributes redirectAttributes) {
        Customer customer = customerService.getCustomerById(khachHangId).orElse(null);
        if (customer == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy khách hàng được chọn!");
            return "redirect:/vaccinations/create";
        }

        record.setKhachHang(customer);
        VaccinationRecord saved = vaccinationService.saveRecord(record);
        redirectAttributes.addFlashAttribute("successMessage",
                "Đã ghi nhận thành công mũi tiêm '" + saved.getTenVacXin() + "' cho khách hàng: " + customer.getHoTen());
        return "redirect:/customers/" + customer.getId();
    }

    /**
     * Giao dien chinh sua mui tiem
     */
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        return vaccinationService.getRecordById(id)
                .map(record -> {
                    model.addAttribute("record", record);
                    model.addAttribute("customers", customerService.getAllCustomers());
                    model.addAttribute("commonVaccines", VaccinationService.COMMON_VACCINES);
                    model.addAttribute("pageTitle", "Chỉnh Sửa Thông Tin Mũi Tiêm");
                    return "vaccinations/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy mũi tiêm có ID: " + id);
                    return "redirect:/vaccinations";
                });
    }

    /**
     * Xu ly cap nhat thong tin mui tiem
     */
    @PostMapping("/{id}/edit")
    public String updateRecord(@PathVariable("id") Long id,
                               @ModelAttribute("record") VaccinationRecord formRecord,
                               @RequestParam("khachHangId") Long khachHangId,
                               RedirectAttributes redirectAttributes) {
        return vaccinationService.getRecordById(id)
                .map(existing -> {
                    Customer customer = customerService.getCustomerById(khachHangId).orElse(existing.getKhachHang());
                    existing.setKhachHang(customer);
                    existing.setTenVacXin(formRecord.getTenVacXin());
                    existing.setMuiSo(formRecord.getMuiSo());
                    existing.setNgayTiem(formRecord.getNgayTiem());
                    existing.setSoLo(formRecord.getSoLo());
                    existing.setCoSoTiem(formRecord.getCoSoTiem());
                    existing.setBacSiKham(formRecord.getBacSiKham());
                    existing.setNguoiTiem(formRecord.getNguoiTiem());
                    existing.setTrangThai(formRecord.getTrangThai());
                    existing.setPhanUngSauTiem(formRecord.getPhanUngSauTiem());
                    existing.setNgayHenMuiTiep(formRecord.getNgayHenMuiTiep());
                    existing.setGhiChu(formRecord.getGhiChu());

                    vaccinationService.saveRecord(existing);
                    redirectAttributes.addFlashAttribute("successMessage", "Cập nhật mũi tiêm thành công!");
                    return "redirect:/customers/" + customer.getId();
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy mũi tiêm có ID: " + id);
                    return "redirect:/vaccinations";
                });
    }

    /**
     * Xoa mui tiem
     */
    @PostMapping("/{id}/delete")
    public String deleteRecord(@PathVariable("id") Long id,
                               @RequestParam(value = "returnToCustomer", required = false) Long customerId,
                               RedirectAttributes redirectAttributes) {
        vaccinationService.deleteRecord(id);
        redirectAttributes.addFlashAttribute("successMessage", "Đã xóa bản ghi mũi tiêm thành công!");
        if (customerId != null) {
            return "redirect:/customers/" + customerId;
        }
        return "redirect:/vaccinations";
    }
}
