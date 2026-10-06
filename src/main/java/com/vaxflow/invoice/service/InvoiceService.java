package com.vaxflow.invoice.service;

import com.vaxflow.invoice.entity.HoaDonKhachHang;
import com.vaxflow.invoice.entity.HoaDonChiTiet;
import com.vaxflow.invoice.entity.PhuongThucThanhToan;
import com.vaxflow.invoice.entity.ThanhToan;
import java.math.BigDecimal;
import java.util.List;

public interface InvoiceService {
    HoaDonKhachHang createInvoiceFromRecords(List<Long> vaccinationRecordIds);

    List<HoaDonKhachHang> findAllInvoices();

    HoaDonKhachHang findInvoice(Long id);

    List<HoaDonChiTiet> findInvoiceLines(Long id);

    ThanhToan payInvoice(Long invoiceId, BigDecimal amount,
            PhuongThucThanhToan paymentMethod, Long cashierId);
}
