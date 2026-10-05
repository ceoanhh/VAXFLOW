package com.vaxflow.invoice.controller;

import com.vaxflow.invoice.entity.HoaDonKhachHang;
import com.vaxflow.invoice.entity.PhuongThucThanhToan;
import com.vaxflow.invoice.service.InvoiceServiceImpl;
import java.util.List;
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

    private final InvoiceServiceImpl invoiceService;

    public InvoiceController(InvoiceServiceImpl invoiceService) {
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
        } catch (RuntimeException exception) {
            redirect.addFlashAttribute("errorMessage", readableMessage(exception));
            return "redirect:/invoices/create";
        }
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("invoice", invoiceService.findInvoice(id));
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
        } catch (RuntimeException exception) {
            redirect.addFlashAttribute("errorMessage", readableMessage(exception));
        }
        return "redirect:/invoices/" + id;
    }

    private String readableMessage(RuntimeException exception) {
        return exception.getMessage() == null ? "Đã xảy ra lỗi khi xử lý hóa đơn." : exception.getMessage();
    }
}
