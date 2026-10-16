package com.vaxflow.core.report;

import java.math.BigDecimal;

/**
 * DTO dung chung cho moi loai bao cao, hien thi tren Chart.js va bang bieu.
 */
public class ReportRow {

    private String label;   // vi du: ten thang, ten vac-xin, ten nhan vien
    private BigDecimal value; // gia tri tuong ung (doanh thu, so ngay cong, so luong ton kho...)

    public ReportRow() {
    }

    public ReportRow(String label, BigDecimal value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ReportRow{" +
                "label='" + label + '\'' +
                ", value=" + value +
                '}';
    }
}
