package com.vaxflow.employee.controller;

import com.vaxflow.employee.dto.DepartmentDTO;
import com.vaxflow.employee.dto.EmployeeDTO;
import com.vaxflow.employee.entity.Gender;
import com.vaxflow.employee.entity.Position;
import com.vaxflow.employee.service.DepartmentService;
import com.vaxflow.employee.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Controller phu trach phan he Nhan su & Phong ban y te
 * Route prefix: /employees
 */
@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

    public EmployeeController(EmployeeService employeeService, DepartmentService departmentService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
    }

    @GetMapping
    public String list(@RequestParam(value = "keyword", required = false) String keyword,
                       @RequestParam(value = "departmentId", required = false) Long departmentId,
                       Model model) {
        List<EmployeeDTO> employees = employeeService.getAll();

        if (keyword != null && !keyword.trim().isEmpty()) {
            String kw = keyword.trim().toLowerCase();
            employees = employees.stream()
                    .filter(e -> (e.getHoVaTen() != null && e.getHoVaTen().toLowerCase().contains(kw))
                            || (e.getMaNhanSu() != null && e.getMaNhanSu().toLowerCase().contains(kw))
                            || (e.getSdt() != null && e.getSdt().contains(kw))
                            || (e.getCccd() != null && e.getCccd().contains(kw)))
                    .toList();
        }

        if (departmentId != null) {
            employees = employees.stream()
                    .filter(e -> e.getIdPhongBan() != null && e.getIdPhongBan().equals(departmentId))
                    .toList();
        }

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
            return "employees/detail";
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy nhân viên với ID: " + id);
            return "redirect:/employees";
        }
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("employee") EmployeeDTO employeeDTO,
                       @RequestParam(value = "departmentId", required = false) Long departmentId,
                       RedirectAttributes redirectAttributes) {
        try {
            if (departmentId != null) {
                employeeDTO.setIdPhongBan(departmentId);
            }
            boolean isNew = (employeeDTO.getId() == null);
            if (isNew) {
                employeeService.create(employeeDTO);
                redirectAttributes.addFlashAttribute("successMessage", "Thêm mới nhân sự thành công!");
            } else {
                employeeService.update(employeeDTO.getId(), employeeDTO);
                redirectAttributes.addFlashAttribute("successMessage", "Cập nhật hồ sơ nhân sự thành công!");
            }
            return "redirect:/employees";
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi lưu dữ liệu: " + ex.getMessage());
            return employeeDTO.getId() == null ? "redirect:/employees/new" : "redirect:/employees/edit/" + employeeDTO.getId();
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
}
