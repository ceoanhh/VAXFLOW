package com.vaxflow.invoice.service;

import com.vaxflow.invoice.entity.HoaDonKhachHang;
import java.util.List;

public interface InvoiceService {
    HoaDonKhachHang createInvoiceFromRecords(List<Long> vaccinationRecordIds);
}
