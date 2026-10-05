package com.vaxflow.invoice.controller;

import java.util.ArrayList;
import java.util.List;

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
                ids.add(Long.valueOf(value));
            }
        }
        return ids;
    }
}
