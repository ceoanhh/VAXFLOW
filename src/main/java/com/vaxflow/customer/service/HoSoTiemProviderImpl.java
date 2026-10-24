package com.vaxflow.customer.service;

import com.vaxflow.customer.entity.HoSo;
import com.vaxflow.customer.entity.KhachHang;
import com.vaxflow.customer.repository.LichSuTiemRepository;
import com.vaxflow.invoice.integration.HoSoTiemProvider;
import com.vaxflow.invoice.integration.HoSoTiemThongTin;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class HoSoTiemProviderImpl implements HoSoTiemProvider {

    private final LichSuTiemRepository lichSuTiemRepository;

    public HoSoTiemProviderImpl(LichSuTiemRepository lichSuTiemRepository) {
        this.lichSuTiemRepository = lichSuTiemRepository;
    }

    @Override
    public List<HoSoTiemThongTin> findRecords(List<Long> vaccinationRecordIds) {
        List<HoSoTiemThongTin> result = new ArrayList<>();
        if (vaccinationRecordIds == null) {
            return result;
        }

        for (Long id : vaccinationRecordIds) {
            lichSuTiemRepository.findById(id).ifPresent(record -> {
                HoSoTiemThongTin info = new HoSoTiemThongTin();
                info.setVaccinationRecordId(record.getId());
                info.setVaccineId(record.getIdVaccine());
                info.setQuantity(1);
                info.setEligibleForInvoice(true);

                KhachHang kh = record.getKhachHang();
                if (kh != null) {
                    info.setCustomerId(kh.getId());
                } else {
                    info.setCustomerId(1L);
                }

                HoSo hs = record.getHoSo();
                if (hs != null) {
                    info.setTreatmentPlanId(hs.getId());
                } else {
                    info.setTreatmentPlanId(1L);
                }

                result.add(info);
            });
        }
        return result;
    }
}