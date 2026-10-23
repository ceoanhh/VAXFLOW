package com.vaxflow.invoice.integration;

import java.math.BigDecimal;

/** DTO mô hình dữ liệu hồ sơ tiêm chủng phục vụ lập hóa đơn và thanh toán. */
public class HoSoTiemThongTin {
    private Long vaccinationRecordId;
    private Long customerId;
    private Long treatmentPlanId;
    private Long vaccineId;
    private Integer quantity;
    private boolean eligibleForInvoice;
    private BigDecimal unitPrice;

    public Long getVaccinationRecordId() { return vaccinationRecordId; }
    public void setVaccinationRecordId(Long vaccinationRecordId) { this.vaccinationRecordId = vaccinationRecordId; }
    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }
    public Long getTreatmentPlanId() { return treatmentPlanId; }
    public void setTreatmentPlanId(Long treatmentPlanId) { this.treatmentPlanId = treatmentPlanId; }
    public Long getVaccineId() { return vaccineId; }
    public void setVaccineId(Long vaccineId) { this.vaccineId = vaccineId; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public boolean isEligibleForInvoice() { return eligibleForInvoice; }
    public void setEligibleForInvoice(boolean eligibleForInvoice) { this.eligibleForInvoice = eligibleForInvoice; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
}
