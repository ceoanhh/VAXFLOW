package com.vaxflow.customer.controller;

import com.vaxflow.customer.entity.HoSo;
import com.vaxflow.customer.entity.KhachHang;
import com.vaxflow.customer.entity.LichSuTiem;
import com.vaxflow.customer.service.CustomerService;
import com.vaxflow.employee.entity.Employee;
import com.vaxflow.employee.repository.EmployeeRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
    private final EmployeeRepository employeeRepository;

    public CustomerController(CustomerService customerService, EmployeeRepository employeeRepository) {
        this.customerService = customerService;
        this.employeeRepository = employeeRepository;
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
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy khách hàng ID: " + id);
            return "redirect:/customers";
        }

        KhachHang customer = customerOpt.get();
        List<HoSo> hoSoList = customerService.findHoSoByKhachHangId(id);
        List<Employee> employees = employeeRepository.findAll();

        // Khởi tạo đối tượng newHoSo và gán sẵn Employee để tránh NullPointerException khi render select option
        HoSo newHoSo = new HoSo();
        newHoSo.setBacSiKham(new Employee());
        newHoSo.setBacSiTiem(new Employee());
        newHoSo.setDieuDuong(new Employee());
        newHoSo.setTiepDon(new Employee());

        model.addAttribute("customer", customer);
        model.addAttribute("hoSoList", hoSoList);
        model.addAttribute("employees", employees);
        model.addAttribute("newHoSo", newHoSo);
        model.addAttribute("newLichSuTiem", new LichSuTiem());

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

    // ================= CRUD HỒ SƠ TIÊM =================

    @PostMapping("/{customerId}/ho-so/save")
    public String saveHoSo(@PathVariable("customerId") Long customerId,
                           @Valid @ModelAttribute("newHoSo") HoSo hoSoForm,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            String errorMsg = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .findFirst()
                    .orElse("Thông tin hồ sơ không hợp lệ!");
            redirectAttributes.addFlashAttribute("errorMessage", errorMsg);
            return "redirect:/customers/detail/" + customerId;
        }

        try {
            KhachHang customer = customerService.findById(customerId)
                    .orElseThrow(() -> new IllegalArgumentException("Khách hàng không tồn tại ID: " + customerId));

            HoSo hoSoToSave;
            if (hoSoForm.getId() != null) {
                hoSoToSave = customerService.findHoSoById(hoSoForm.getId())
                        .orElseThrow(() -> new IllegalArgumentException("Hồ sơ không tồn tại ID: " + hoSoForm.getId()));
                hoSoToSave.setMaHoSo(hoSoForm.getMaHoSo());
                hoSoToSave.setGhiChuTheoDoi(hoSoForm.getGhiChuTheoDoi());
            } else {
                hoSoToSave = hoSoForm;
                hoSoToSave.setKhachHang(customer);
            }

            // Gán nhân viên đã qua kiểm tra
            if (hoSoForm.getBacSiKham() != null && hoSoForm.getBacSiKham().getId() != null) {
                hoSoToSave.setBacSiKham(employeeRepository.findById(hoSoForm.getBacSiKham().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy Bác sĩ khám")));
            }
            if (hoSoForm.getBacSiTiem() != null && hoSoForm.getBacSiTiem().getId() != null) {
                hoSoToSave.setBacSiTiem(employeeRepository.findById(hoSoForm.getBacSiTiem().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy Bác sĩ tiêm")));
            }
            if (hoSoForm.getDieuDuong() != null && hoSoForm.getDieuDuong().getId() != null) {
                hoSoToSave.setDieuDuong(employeeRepository.findById(hoSoForm.getDieuDuong().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy Điều dưỡng")));
            }
            if (hoSoForm.getTiepDon() != null && hoSoForm.getTiepDon().getId() != null) {
                hoSoToSave.setTiepDon(employeeRepository.findById(hoSoForm.getTiepDon().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy Nhân viên tiếp đón")));
            }

            customerService.saveHoSo(hoSoToSave);
            redirectAttributes.addFlashAttribute("successMessage",
                    hoSoForm.getId() != null ? "Cập nhật hồ sơ tiêm thành công!" : "Tạo hồ sơ tiêm mới thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi lưu hồ sơ: " + e.getMessage());
        }
        return "redirect:/customers/detail/" + customerId;
    }

    // ================= CRUD LỊCH SỬ TIÊM =================

    @PostMapping("/{customerId}/ho-so/{hoSoId}/lich-su/save")
    public String saveLichSuTiem(@PathVariable("customerId") Long customerId,
                                 @PathVariable("hoSoId") Long hoSoId,
                                 @Valid @ModelAttribute("newLichSuTiem") LichSuTiem lichSuForm,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            String errorMsg = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .findFirst()
                    .orElse("Thông tin mũi tiêm không hợp lệ!");
            redirectAttributes.addFlashAttribute("errorMessage", errorMsg);
            return "redirect:/customers/detail/" + customerId;
        }

        try {
            HoSo hoSo = customerService.findHoSoById(hoSoId)
                    .orElseThrow(() -> new IllegalArgumentException("Hồ sơ không tồn tại"));
            KhachHang customer = customerService.findById(customerId)
                    .orElseThrow(() -> new IllegalArgumentException("Khách hàng không tồn tại"));

            LichSuTiem recordToSave;
            if (lichSuForm.getId() != null) {
                recordToSave = customerService.findLichSuTiemById(lichSuForm.getId())
                        .orElseThrow(() -> new IllegalArgumentException("Bản ghi tiêm không tồn tại"));
                recordToSave.setMaTiem(lichSuForm.getMaTiem());
                recordToSave.setIdVaccine(lichSuForm.getIdVaccine());
                recordToSave.setIdLoVaccine(lichSuForm.getIdLoVaccine());
                recordToSave.setSoMuiTiem(lichSuForm.getSoMuiTiem());
                recordToSave.setNgayTiem(lichSuForm.getNgayTiem());
                recordToSave.setTrangThai(lichSuForm.getTrangThai());
            } else {
                recordToSave = lichSuForm;
                recordToSave.setHoSo(hoSo);
                recordToSave.setKhachHang(customer);
            }

            customerService.saveLichSuTiem(recordToSave);
            redirectAttributes.addFlashAttribute("successMessage",
                    lichSuForm.getId() != null ? "Cập nhật mũi tiêm thành công!" : "Thêm mũi tiêm mới thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi lưu lịch sử tiêm: " + e.getMessage());
        }
        return "redirect:/customers/detail/" + customerId;
    }
}
