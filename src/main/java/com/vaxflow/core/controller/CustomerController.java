package com.vaxflow.core.controller;

import com.vaxflow.core.entity.Customer;
import com.vaxflow.core.service.CustomerService;
import com.vaxflow.core.service.VaccinationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Controller quan ly khach hang / nguoi tiem cho Admin
 * Route prefix: /customers
 */
@Controller
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final VaccinationService vaccinationService;

    public CustomerController(CustomerService customerService, VaccinationService vaccinationService) {
        this.customerService = customerService;
        this.vaccinationService = vaccinationService;
    }

    /**
     * Danh sach tat ca khach hang (kem tim kiem)
     */
    @GetMapping
    public String listCustomers(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<Customer> customers = customerService.searchCustomers(keyword);
        model.addAttribute("customers", customers);
        model.addAttribute("keyword", keyword);
        model.addAttribute("totalCustomers", customerService.countCustomers());
        return "customers/list";
    }

    /**
     * Giao dien them moi khach hang
     */
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("customer", new Customer());
        model.addAttribute("pageTitle", "Thêm Mới Khách Hàng / Người Tiêm");
        return "customers/form";
    }

    /**
     * Xu ly luu khach hang moi
     */
    @PostMapping("/create")
    public String createCustomer(@ModelAttribute("customer") Customer customer, RedirectAttributes redirectAttributes) {
        Customer saved = customerService.saveCustomer(customer);
        redirectAttributes.addFlashAttribute("successMessage", "Đã thêm thành công hồ sơ khách hàng: " + saved.getHoTen());
        return "redirect:/customers/" + saved.getId();
    }

    /**
     * Xem chi tiet ho so khach hang va lich su cac mui tiem
     */
    @GetMapping("/{id}")
    public String viewCustomerDetail(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        return customerService.getCustomerById(id)
                .map(customer -> {
                    model.addAttribute("customer", customer);
                    model.addAttribute("records", vaccinationService.getRecordsByCustomerId(id));
                    return "customers/detail";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy khách hàng có ID: " + id);
                    return "redirect:/customers";
                });
    }

    /**
     * Giao dien chinh sua khach hang
     */
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        return customerService.getCustomerById(id)
                .map(customer -> {
                    model.addAttribute("customer", customer);
                    model.addAttribute("pageTitle", "Chỉnh Sửa Thông Tin Khách Hàng");
                    return "customers/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy khách hàng có ID: " + id);
                    return "redirect:/customers";
                });
    }

    /**
     * Xu ly cap nhat thong tin khach hang
     */
    @PostMapping("/{id}/edit")
    public String updateCustomer(@PathVariable("id") Long id, @ModelAttribute("customer") Customer formCustomer, RedirectAttributes redirectAttributes) {
        return customerService.getCustomerById(id)
                .map(existing -> {
                    existing.setHoTen(formCustomer.getHoTen());
                    existing.setNgaySinh(formCustomer.getNgaySinh());
                    existing.setGioiTinh(formCustomer.getGioiTinh());
                    existing.setSoDienThoai(formCustomer.getSoDienThoai());
                    existing.setCccd(formCustomer.getCccd());
                    existing.setDiaChi(formCustomer.getDiaChi());
                    existing.setNguoiGiamHo(formCustomer.getNguoiGiamHo());
                    existing.setSoDienThoaiGiamHo(formCustomer.getSoDienThoaiGiamHo());
                    existing.setTienSuBenh(formCustomer.getTienSuBenh());
                    existing.setGhiChu(formCustomer.getGhiChu());

                    customerService.saveCustomer(existing);
                    redirectAttributes.addFlashAttribute("successMessage", "Cập nhật thông tin khách hàng thành công!");
                    return "redirect:/customers/" + id;
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy khách hàng có ID: " + id);
                    return "redirect:/customers";
                });
    }

    /**
     * Xoa khach hang
     */
    @PostMapping("/{id}/delete")
    public String deleteCustomer(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        customerService.deleteCustomer(id);
        redirectAttributes.addFlashAttribute("successMessage", "Đã xóa thành công hồ sơ khách hàng!");
        return "redirect:/customers";
    }
}
