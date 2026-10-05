package com.vaxflow.invoice.integration;

import java.math.BigDecimal;

/** Person 2 or Person 5 supplies the current vaccine price when it is absent from the record. */
public interface GiaVaccineProvider {
    BigDecimal findUnitPrice(Long vaccineId);
}
