package com.vaxflow.employee.controller;

import com.vaxflow.employee.dto.DepartmentDTO;
import com.vaxflow.employee.dto.EmployeeDTO;
import com.vaxflow.employee.dto.PayrollDTO;
import com.vaxflow.employee.entity.Gender;
import com.vaxflow.employee.entity.Position;
import com.vaxflow.employee.service.DepartmentService;
import com.vaxflow.employee.service.EmployeeService;
import com.vaxflow.employee.service.PayrollService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final DepartmentService departmentService;
    private final PayrollService payrollService;

    public EmployeeController(EmployeeService employeeService, DepartmentService departmentService, PayrollService payrollService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
        this.payrollService = payrollService;
    }

    @GetMapping
    public String list(@RequestParam(value = "keyword", required = false) String keyword,
                   @RequestParam(value = "departmentId", required = false) Long departmentId,
                   Model model) {
    
        List<EmployeeDTO> employees = employeeService.search(keyword, departmentId);

        List<DepartmentDTO> departments = departmentService.getAll();

        model.addAttribute("employees", employees);
        model.addAttribute("departments", departments);
        model.addAttribute("keyword", keyword);
        model.addAttribute("selectedDepartmentId", departmentId);
        model.addAttribute("totalCount", employees.size());

        return "employees/index";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        EmployeeDTO employee = new EmployeeDTO();
        List<DepartmentDTO> departments = departmentService.getAll();

        model.addAttribute("employee", employee);
        model.addAttribute("departments", departments);
        model.addAttribute("positions", Position.values());
        model.addAttribute("genders", Gender.values());
        model.addAttribute("isEdit", false);

        return "employees/form";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            EmployeeDTO employee = employeeService.getById(id);
            List<DepartmentDTO> departments = departmentService.getAll();

            model.addAttribute("employee", employee);
            model.addAttribute("departments", departments);
            model.addAttribute("positions", Position.values());
            model.addAttribute("genders", Gender.values());
            model.addAttribute("isEdit", true);

            return "employees/form";
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy nhân viên với ID: " + id);
            return "redirect:/employees";
        }
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            EmployeeDTO employee = employeeService.getById(id);
            DepartmentDTO department = null;
            if (employee.getIdPhongBan() != null) {
                try {
                    department = departmentService.getById(employee.getIdPhongBan());
                } catch (Exception ignored) {
                }
            }
            model.addAttribute("employee", employee);
            model.addAttribute("department", department);
            
            // 1. Kéo danh sách bảng lương đã lọc & sắp xếp sẵn từ Service
            List<PayrollDTO> payrolls = payrollService.getPayrollsByNhanSuId(id);

            // 2. Lấy bản ghi đầu tiên (mới nhất), nếu không có thì gán null
            PayrollDTO latestPayroll = payrolls.isEmpty() ? null : payrolls.get(0);

            // 3. Truyền ra ngoài giao diện HTML
            model.addAttribute("latestPayroll", latestPayroll);
            return "employees/detail";
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy nhân viên với ID: " + id);
            return "redirect:/employees";
        }
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("employee") EmployeeDTO employeeDTO,
                    @RequestParam(value = "departmentId", required = false) Long departmentId,
                    RedirectAttributes redirectAttributes,
                    Model model) { // 1. Bổ sung tham số Model vào đây
        try {
            if (departmentId != null) employeeDTO.setIdPhongBan(departmentId);
            
            boolean isNew = (employeeDTO.getId() == null);
            if (isNew) {
                employeeService.create(employeeDTO);
                redirectAttributes.addFlashAttribute("successMessage", "Thêm mới nhân sự thành công!");
            } else {
                employeeService.update(employeeDTO.getId(), employeeDTO);
                redirectAttributes.addFlashAttribute("successMessage", "Cập nhật hồ sơ thành công!");
            }
            return "redirect:/employees";
            
        } catch (RuntimeException ex) {
            // 2. Truyền trực tiếp thông báo lỗi vào Model hiện tại
            model.addAttribute("errorMessage", ex.getMessage());
            
            // 3. Nạp lại các danh sách dropdown để form HTML không bị sập (lấy y hệt như hàm createForm)
            model.addAttribute("departments", departmentService.getAll());
            model.addAttribute("positions", Position.values());
            model.addAttribute("genders", Gender.values());
            model.addAttribute("isEdit", employeeDTO.getId() != null);
            
            // Tự động xóa trắng ô CCCD khi có lỗi trùng CCCD
            if (ex.getMessage() != null) {
                if (ex.getMessage().toLowerCase().contains("cccd") || ex.getMessage().toLowerCase().contains("unique")) {
                    employeeDTO.setCccd(""); 
                }
                if (ex.getMessage().toLowerCase().contains("sđt") || ex.getMessage().toLowerCase().contains("điện thoại")) {
                    employeeDTO.setSdt(""); 
                }
                if (ex.getMessage().toLowerCase().contains("email")) {
                    employeeDTO.setEmail(""); 
                }
            }
            
            // 4. Trả thẳng về view HTML (KHÔNG dùng "redirect:")
            return "employees/form";
        }
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            employeeService.delete(id);
            redirectAttributes.addFlashAttribute("successMessage", "Đã xóa nhân viên thành công!");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không thể xóa nhân viên do có dữ liệu liên kết!");
        }
        return "redirect:/employees";
    }

    @PostMapping("/{id}/payrolls/save")
    public String savePayroll(@PathVariable Long id, @ModelAttribute("payroll") PayrollDTO payrollDTO) {
        payrollDTO.setIdNhanSu(id); 
        payrollService.create(payrollDTO);
        return "redirect:/employees/" + id + "/payrolls";
    }

    @GetMapping("/{id}/payrolls/create")
    public String showCreatePayrollForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            EmployeeDTO employee = employeeService.getById(id);
            PayrollDTO payroll = new PayrollDTO();
            
            // Tự động điền mức lương cứng từ hồ sơ nhân viên vào form
            payroll.setLuongCung(employee.getLuongCung());
            
            model.addAttribute("employee", employee);
            model.addAttribute("payroll", payroll);
            return "employees/payroll_form";
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy nhân viên.");
            return "redirect:/employees";
        }
    }

    // Hàm mở màn hình Xem tất cả lịch sử bảng lương
    @GetMapping("/{id}/payrolls")
    public String showAllPayrolls(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            EmployeeDTO employee = employeeService.getById(id);
            
            // Controller chỉ việc gọi Service, không cần tính toán gì thêm
            List<PayrollDTO> payrolls = payrollService.getPayrollsByNhanSuId(id);
            
            model.addAttribute("employee", employee);
            model.addAttribute("payrolls", payrolls);
            return "employees/payrolls_history";
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy dữ liệu.");
            return "redirect:/employees";
        }
    }

    @PostMapping("/{empId}/payrolls/delete/{payrollId}")
    public String deletePayroll(@PathVariable("empId") Long empId, @PathVariable("payrollId") Long payrollId) {
        // Gọi hàm xóa bảng lương
        payrollService.delete(payrollId);
        
        // Xóa xong thì load lại (redirect) về trang lịch sử lương của nhân viên đó
        return "redirect:/employees/" + empId + "/payrolls";
    }
}
