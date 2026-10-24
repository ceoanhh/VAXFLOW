package com.vaxflow.customer.service;

import com.vaxflow.customer.entity.HoSo;
import com.vaxflow.customer.entity.KhachHang;
import com.vaxflow.customer.entity.LichSuTiem;
import com.vaxflow.customer.repository.HoSoRepository;
import com.vaxflow.customer.repository.KhachHangRepository;
import com.vaxflow.customer.repository.LichSuTiemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerService {

    private final KhachHangRepository khachHangRepository;
    private final LichSuTiemRepository lichSuTiemRepository;
    private final HoSoRepository hoSoRepository;

    public CustomerService(KhachHangRepository khachHangRepository, LichSuTiemRepository lichSuTiemRepository, HoSoRepository hoSoRepository) {
        this.khachHangRepository = khachHangRepository;
        this.lichSuTiemRepository = lichSuTiemRepository;
        this.hoSoRepository = hoSoRepository;
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
    public List<HoSo> findHoSoByKhachHangId(Long khachHangId) {
        return hoSoRepository.findAll().stream()
                .filter(h -> h.getKhachHang() != null && h.getKhachHang().getId().equals(khachHangId))
                .toList();
    }

    @Transactional(readOnly = true)
    public Optional<HoSo> findHoSoById(Long hoSoId) {
        return hoSoRepository.findById(hoSoId);
    }

    public HoSo saveHoSo(HoSo hoSo) {
        return hoSoRepository.save(hoSo);
    }

    public void deleteHoSoById(Long id) {
        hoSoRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Optional<LichSuTiem> findLichSuTiemById(Long id) {
        return lichSuTiemRepository.findById(id);
    }

    public LichSuTiem saveLichSuTiem(LichSuTiem lichSuTiem) {
        return lichSuTiemRepository.save(lichSuTiem);
    }

    public void deleteLichSuTiemById(Long id) {
        lichSuTiemRepository.deleteById(id);
    }
}
