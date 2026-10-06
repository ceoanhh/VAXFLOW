package com.vaxflow.vaccine.service;

import com.vaxflow.invoice.integration.GiaVaccineProvider;
import com.vaxflow.vaccine.entity.VacXin;
import com.vaxflow.vaccine.repository.VacXinRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class GiaVaccineProviderImpl implements GiaVaccineProvider {

    private final VacXinRepository vacXinRepository;

    public GiaVaccineProviderImpl(VacXinRepository vacXinRepository) {
        this.vacXinRepository = vacXinRepository;
    }

    @Override
    public BigDecimal findUnitPrice(Long vaccineId) {
        return vacXinRepository.findById(vaccineId)
                .map(VacXin::getDonGia)
                .orElse(BigDecimal.ZERO);
    }
}
