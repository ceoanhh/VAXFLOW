package com.vaxflow.invoice.controller;

import java.util.ArrayList;
import java.util.List;
import com.vaxflow.invoice.service.InvoiceBusinessException;

public class TaoHoaDonForm {
    private String vaccinationRecordIds;

    public String getVaccinationRecordIds() { return vaccinationRecordIds; }
    public void setVaccinationRecordIds(String vaccinationRecordIds) { this.vaccinationRecordIds = vaccinationRecordIds; }

    public List<Long> parseRecordIds() {
        List<Long> ids = new ArrayList<>();
        if (vaccinationRecordIds == null || vaccinationRecordIds.isBlank()) {
            return ids;
        }
        for (String value : vaccinationRecordIds.split("[,\\s]+")) {
            if (!value.isBlank()) {
                try {
                    ids.add(Long.valueOf(value));
                } catch (NumberFormatException exception) {
                    throw new InvoiceBusinessException("Mã hồ sơ tiêm không hợp lệ: " + value);
                }
            }
        }
        return ids;
    }
}
