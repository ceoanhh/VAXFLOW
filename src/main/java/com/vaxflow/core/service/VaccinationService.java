package com.vaxflow.core.service;

import com.vaxflow.core.entity.VaccinationRecord;
import com.vaxflow.core.repository.VaccinationRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VaccinationService {

    private final VaccinationRecordRepository vaccinationRecordRepository;

    // Danh sach vac-xin chuan y te de lua chon trong he thong
    public static final List<String> COMMON_VACCINES = Arrays.asList(
            "Hexaxim (6 trong 1) - Sanofi (Pháp)",
            "Infanrix Hexa (6 trong 1) - GSK (Bỉ)",
            "Synflorix (Phế cầu 10) - GSK (Bỉ)",
            "Prevenar 13 (Phế cầu 13) - Pfizer (Bỉ)",
            "Gardasil 9 (HPV 9 chủng) - MSD (Mỹ)",
            "Influvac Tetra (Cúm mùa) - Abbott (Hà Lan)",
            "Rotavin-M1 (Rota virus) - Polyvac (Việt Nam)",
            "Imojev (Viêm não Nhật Bản) - Sanofi (Thái Lan)",
            "Varilrix (Thủy đậu) - GSK (Bỉ)",
            "Boostrix (Bạch hầu - Uốn ván - Ho gà nhắc) - GSK (Bỉ)",
            "Engerix B (Viêm gan B) - GSK (Bỉ)",
            "BCG (Lao đông khô) - IVAC (Việt Nam)",
            "MMR II (Sởi - Quai bị - Rubella) - MSD (Mỹ)",
            "Menactra (Não mô cầu ACYW-135) - Sanofi (Mỹ)"
    );

    public VaccinationService(VaccinationRecordRepository vaccinationRecordRepository) {
        this.vaccinationRecordRepository = vaccinationRecordRepository;
    }

    public List<VaccinationRecord> getAllRecords() {
        return vaccinationRecordRepository.findAllByOrderByNgayTiemDesc();
    }

    public List<VaccinationRecord> searchRecords(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllRecords();
        }
        return vaccinationRecordRepository.searchRecords(keyword.trim());
    }

    public List<VaccinationRecord> getRecordsByCustomerId(Long customerId) {
        return vaccinationRecordRepository.findByKhachHangIdOrderByNgayTiemDesc(customerId);
    }

    public Optional<VaccinationRecord> getRecordById(Long id) {
        return vaccinationRecordRepository.findById(id);
    }

    public VaccinationRecord saveRecord(VaccinationRecord record) {
        return vaccinationRecordRepository.save(record);
    }

    public void deleteRecord(Long id) {
        vaccinationRecordRepository.deleteById(id);
    }

    public long countTotalDoses() {
        return vaccinationRecordRepository.count();
    }

    public long countAdministeredDoses() {
        return vaccinationRecordRepository.countByTrangThai("DA_TIEM");
    }

    public long countScheduledDoses() {
        return vaccinationRecordRepository.countByTrangThai("HEN_TIEM");
    }

    public long countTodayDoses() {
        return vaccinationRecordRepository.countByNgayTiem(LocalDate.now());
    }
}
