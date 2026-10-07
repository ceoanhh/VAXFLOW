package com.vaxflow.invoice.integration;

import java.math.BigDecimal;

/** Cung cấp đơn giá vắc xin hiện hành từ phân hệ kho dược. */
public interface GiaVaccineProvider {
    BigDecimal findUnitPrice(Long vaccineId);
}
