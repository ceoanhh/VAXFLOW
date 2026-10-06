package com.vaxflow.invoice.controller;

import com.vaxflow.invoice.entity.PhuongThucThanhToan;
import java.math.BigDecimal;

public class ThanhToanForm {
    private BigDecimal soTien;
    private PhuongThucThanhToan phuongThucTt;

    public BigDecimal getSoTien() { return soTien; }
    public void setSoTien(BigDecimal soTien) { this.soTien = soTien; }
    public PhuongThucThanhToan getPhuongThucTt() { return phuongThucTt; }
    public void setPhuongThucTt(PhuongThucThanhToan phuongThucTt) { this.phuongThucTt = phuongThucTt; }
}
