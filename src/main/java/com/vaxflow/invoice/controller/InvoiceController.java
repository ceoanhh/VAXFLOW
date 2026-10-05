package com.vaxflow.invoice.controller;

import com.vaxflow.invoice.entity.HoaDonKhachHang;
import com.vaxflow.invoice.entity.PhuongThucThanhToan;
import com.vaxflow.invoice.service.InvoiceBusinessException;
import com.vaxflow.invoice.service.InvoiceService;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("invoices", invoiceService.findAllInvoices());
        return "invoice/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("form", new TaoHoaDonForm());
        return "invoice/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("form") TaoHoaDonForm form, RedirectAttributes redirect) {
        try {
            HoaDonKhachHang invoice = invoiceService.createInvoiceFromRecords(form.parseRecordIds());
            redirect.addFlashAttribute("successMessage", "Đã tạo hóa đơn " + invoice.getMaHoaDon() + ".");
            return "redirect:/invoices/" + invoice.getId();
        } catch (InvoiceBusinessException exception) {
            redirect.addFlashAttribute("errorMessage", exception.getMessage());
            return "redirect:/invoices/create";
        } catch (DataIntegrityViolationException exception) {
            redirect.addFlashAttribute("errorMessage", isDuplicateVaccinationRecord(exception)
                    ? "Hồ sơ tiêm đã được lập hóa đơn."
                    : "Không thể tạo hóa đơn do dữ liệu bị trùng hoặc không hợp lệ.");
            return "redirect:/invoices/create";
        } catch (RuntimeException exception) {
            redirect.addFlashAttribute("errorMessage", "Đã xảy ra lỗi khi tạo hóa đơn. Vui lòng thử lại.");
            return "redirect:/invoices/create";
        }
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model, RedirectAttributes redirect) {
        HoaDonKhachHang invoice;
        try {
            invoice = invoiceService.findInvoice(id);
        } catch (InvoiceBusinessException exception) {
            redirect.addFlashAttribute("errorMessage", exception.getMessage());
            return "redirect:/invoices";
        }
        model.addAttribute("invoice", invoice);
        model.addAttribute("lines", invoiceService.findInvoiceLines(id));
        model.addAttribute("paymentForm", new ThanhToanForm());
        model.addAttribute("paymentMethods", List.of(PhuongThucThanhToan.values()));
        return "invoice/detail";
    }

    @PostMapping("/{id}/pay")
    public String pay(@PathVariable Long id, @ModelAttribute("paymentForm") ThanhToanForm form,
            RedirectAttributes redirect) {
        try {
            invoiceService.payInvoice(id, form.getSoTien(), form.getPhuongThucTt(), null);
            redirect.addFlashAttribute("successMessage", "Thanh toán hóa đơn thành công.");
        } catch (InvoiceBusinessException exception) {
            redirect.addFlashAttribute("errorMessage", exception.getMessage());
        } catch (DataIntegrityViolationException exception) {
            redirect.addFlashAttribute("errorMessage", "Không thể ghi nhận thanh toán do dữ liệu bị trùng hoặc không hợp lệ.");
        } catch (RuntimeException exception) {
            redirect.addFlashAttribute("errorMessage", "Đã xảy ra lỗi khi thanh toán. Vui lòng thử lại.");
        }
        return "redirect:/invoices/" + id;
    }

    private boolean isDuplicateVaccinationRecord(DataIntegrityViolationException exception) {
        Throwable cause = exception;
        while (cause != null) {
            String message = cause.getMessage();
            if (message != null) {
                String normalized = message.toLowerCase(java.util.Locale.ROOT);
                boolean namesRecordColumn = normalized.contains("id_tiem");
                boolean namesUniqueViolation = normalized.contains("duplicate")
                        || normalized.contains("unique")
                        || normalized.contains("uk_hoa_don_chi_tiet_id_tiem");
                if (namesRecordColumn && namesUniqueViolation) {
                    return true;
                }
            }
            cause = cause.getCause();
        }
        return false;
    }
}
