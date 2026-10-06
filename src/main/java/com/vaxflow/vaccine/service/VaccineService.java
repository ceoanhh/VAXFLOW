package com.vaxflow.vaccine.service;

import com.vaxflow.vaccine.entity.LoVacXin;
import com.vaxflow.vaccine.entity.VacXin;
import com.vaxflow.vaccine.repository.LoVacXinRepository;
import com.vaxflow.vaccine.repository.VacXinRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VaccineService {

    private final VacXinRepository vacXinRepository;
    private final LoVacXinRepository loVacXinRepository;

    public VaccineService(VacXinRepository vacXinRepository, LoVacXinRepository loVacXinRepository) {
        this.vacXinRepository = vacXinRepository;
        this.loVacXinRepository = loVacXinRepository;
    }

    @Transactional(readOnly = true)
    public List<VacXin> findAll() {
        return vacXinRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<VacXin> findById(Long id) {
        return vacXinRepository.findById(id);
    }

    public VacXin save(VacXin vacXin) {
        return vacXinRepository.save(vacXin);
    }

    public void deleteById(Long id) {
        vacXinRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<LoVacXin> findAllBatches() {
        return loVacXinRepository.findAll();
    }

    public LoVacXin saveBatch(LoVacXin loVacXin) {
        return loVacXinRepository.save(loVacXin);
    }

    @Transactional(readOnly = true)
    public long count() {
        return vacXinRepository.count();
    }

    @Transactional(readOnly = true)
    public long countLowStock() {
        // Dem cac lo vac xin co so luong con lai <= 10
        return loVacXinRepository.findAll().stream()
                .filter(l -> l.getSoLuongConLai() <= 10)
                .count();
    }
}
