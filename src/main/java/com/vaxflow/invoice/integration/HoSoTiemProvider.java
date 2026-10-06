package com.vaxflow.invoice.integration;

import java.util.List;

/** Person 2 supplies an adapter that reads and validates its actual vaccination records. */
public interface HoSoTiemProvider {
    List<HoSoTiemThongTin> findRecords(List<Long> vaccinationRecordIds);
}
