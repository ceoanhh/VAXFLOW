package com.vaxflow.core.service;

import com.vaxflow.core.entity.KhachHang;
import com.vaxflow.core.entity.LichSuTiem;
import com.vaxflow.core.repository.KhachHangRepository;
import com.vaxflow.core.repository.LichSuTiemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerService {

    private final KhachHangRepository khachHangRepository;
    private final LichSuTiemRepository lichSuTiemRepository;

    public CustomerService(KhachHangRepository khachHangRepository, LichSuTiemRepository lichSuTiemRepository) {
        this.khachHangRepository = khachHangRepository;
        this.lichSuTiemRepository = lichSuTiemRepository;
    }

    @Transactional(readOnly = true)
    public List<KhachHang> findAll() {
        return khachHangRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<KhachHang> findById(Long id) {
        return khachHangRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<KhachHang> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return khachHangRepository.findAll();
        }
        String trimmed = keyword.trim();
        return khachHangRepository.findByTenKhachHangContainingIgnoreCaseOrSdtContaining(trimmed, trimmed);
    }

    public KhachHang save(KhachHang khachHang) {
        return khachHangRepository.save(khachHang);
    }

    public void deleteById(Long id) {
        khachHangRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public long count() {
        return khachHangRepository.count();
    }

    @Transactional(readOnly = true)
    public List<LichSuTiem> findAllRecords() {
        return lichSuTiemRepository.findAll();
    }

    public LichSuTiem saveRecord(LichSuTiem record) {
        return lichSuTiemRepository.save(record);
    }
}
