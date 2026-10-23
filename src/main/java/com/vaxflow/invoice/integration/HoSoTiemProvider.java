package com.vaxflow.invoice.integration;

import java.util.List;

/** Interface cung cấp và xác thực dữ liệu hồ sơ tiêm chủng cho phân hệ thanh toán. */
public interface HoSoTiemProvider {
    List<HoSoTiemThongTin> findRecords(List<Long> vaccinationRecordIds);
}
