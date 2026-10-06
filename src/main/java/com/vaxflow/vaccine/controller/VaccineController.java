package com.vaxflow.vaccine.controller;

import com.vaxflow.vaccine.entity.LoVacXin;
import com.vaxflow.vaccine.entity.VacXin;
import com.vaxflow.vaccine.service.VaccineService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Controller phu trach phan he Vac xin & Kho (Person 5 - Truong)
 * Route prefix: /vaccines
 */
@Controller
@RequestMapping("/vaccines")
public class VaccineController {

    private final VaccineService vaccineService;

    public VaccineController(VaccineService vaccineService) {
        this.vaccineService = vaccineService;
    }

    @GetMapping
    public String list(Model model) {
        List<VacXin> vaccines = vaccineService.findAll();
        List<LoVacXin> batches = vaccineService.findAllBatches();

        model.addAttribute("vaccines", vaccines);
        model.addAttribute("batches", batches);
        model.addAttribute("totalVaccines", vaccines.size());
        model.addAttribute("totalBatches", batches.size());
        model.addAttribute("lowStockCount", vaccineService.countLowStock());

        return "vaccines/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("vaccine", new VacXin());
        model.addAttribute("isEdit", false);
        return "vaccines/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("vaccine") VacXin vaccine, RedirectAttributes redirectAttributes) {
        try {
            boolean isNew = (vaccine.getId() == null);
            vaccineService.save(vaccine);
            redirectAttributes.addFlashAttribute("successMessage",
                    isNew ? "Thêm vắc xin mới thành công!" : "Cập nhật vắc xin thành công!");
            return "redirect:/vaccines";
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi lưu vắc xin: " + ex.getMessage());
            return "redirect:/vaccines/new";
        }
    }

    @GetMapping("/batch/new")
    public String createBatchForm(Model model) {
        LoVacXin batch = new LoVacXin();
        model.addAttribute("batch", batch);
        model.addAttribute("vaccines", vaccineService.findAll());
        return "vaccines/batch_form";
    }

    @PostMapping("/batch/save")
    public String saveBatch(@ModelAttribute("batch") LoVacXin batch,
                            @RequestParam("vaccineId") Long vaccineId,
                            RedirectAttributes redirectAttributes) {
        try {
            Optional<VacXin> vacXinOpt = vaccineService.findById(vaccineId);
            if (vacXinOpt.isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Loại vắc xin không hợp lệ!");
                return "redirect:/vaccines/batch/new";
            }
            batch.setVacXin(vacXinOpt.get());
            if (batch.getNgayNhap() == null) {
                batch.setNgayNhap(LocalDateTime.now());
            }
            if (batch.getSoLuongConLai() == 0 && batch.getSoLuongNhap() > 0) {
                batch.setSoLuongConLai(batch.getSoLuongNhap());
            }
            vaccineService.saveBatch(batch);
            redirectAttributes.addFlashAttribute("successMessage", "Nhập lô vắc xin thành công!");
            return "redirect:/vaccines";
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi nhập lô vắc xin: " + ex.getMessage());
            return "redirect:/vaccines/batch/new";
        }
    }
}
