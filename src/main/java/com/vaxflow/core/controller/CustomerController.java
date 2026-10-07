package com.vaxflow.core.controller;

import com.vaxflow.core.entity.KhachHang;
import com.vaxflow.core.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

/**
 * Controller phu trach phan he Khach hang & Ho so tiem chung y te
 * Route prefix: /customers
 */
@Controller
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public String list(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<KhachHang> customers = customerService.search(keyword);
        model.addAttribute("customers", customers);
        model.addAttribute("keyword", keyword);
        model.addAttribute("totalCount", customers.size());
        return "customers/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        KhachHang customer = new KhachHang();
        model.addAttribute("customer", customer);
        model.addAttribute("isEdit", false);
        return "customers/form";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<KhachHang> customerOpt = customerService.findById(id);
        if (customerOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy khách hàng với ID: " + id);
            return "redirect:/customers";
        }
        model.addAttribute("customer", customerOpt.get());
        model.addAttribute("isEdit", true);
        return "customers/form";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<KhachHang> customerOpt = customerService.findById(id);
        if (customerOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy khách hàng với ID: " + id);
            return "redirect:/customers";
        }
        model.addAttribute("customer", customerOpt.get());
        return "customers/detail";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("customer") KhachHang customer, RedirectAttributes redirectAttributes) {
        try {
            boolean isNew = (customer.getId() == null);
            customerService.save(customer);
            redirectAttributes.addFlashAttribute("successMessage",
                    isNew ? "Thêm mới hồ sơ khách hàng thành công!" : "Cập nhật thông tin khách hàng thành công!");
            return "redirect:/customers";
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi lưu dữ liệu: " + ex.getMessage());
            return customer.getId() == null ? "redirect:/customers/new" : "redirect:/customers/edit/" + customer.getId();
        }
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            customerService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Đã xóa khách hàng thành công!");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không thể xóa khách hàng do có dữ liệu liên kết!");
        }
        return "redirect:/customers";
    }
}
