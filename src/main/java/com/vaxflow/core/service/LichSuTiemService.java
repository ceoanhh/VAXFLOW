package com.vaxflow.core.service;

import com.vaxflow.core.entity.LichSuTiem;
import com.vaxflow.core.repository.LichSuTiemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class LichSuTiemService {

    private final LichSuTiemRepository lichSuTiemRepository;

    public LichSuTiemService(LichSuTiemRepository lichSuTiemRepository) {
        this.lichSuTiemRepository = lichSuTiemRepository;
    }

    public Page<LichSuTiem> getAllLichSuTiem(Pageable pageable) {
        return lichSuTiemRepository.findAll(pageable);
    }

    public LichSuTiem getLichSuTiemById(Long id) {
        return lichSuTiemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy lịch sử tiêm với ID: " + id));
    }

    public LichSuTiem getLichSuTiemByMa(String maTiem) {
        return lichSuTiemRepository.findByMaTiem(maTiem)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy lịch sử tiêm với mã: " + maTiem));
    }

    public Page<LichSuTiem> getLichSuTiemByKhachHang(Long idKhachHang, Pageable pageable) {
        return lichSuTiemRepository.findByKhachHangId(idKhachHang, pageable);
    }

    public List<LichSuTiem> getLichSuTiemByVaccine(Long idVaccine) {
        return lichSuTiemRepository.findByIdVaccine(idVaccine);
    }

    public List<LichSuTiem> getLichSuTiemTrongKhoangNgay(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("startDate không được lớn hơn endDate");
        }
        return lichSuTiemRepository.findByNgayTiemBetween(startDate, endDate);
    }

    @Transactional
    public LichSuTiem createLichSuTiem(LichSuTiem lichSuTiem) {
        if (lichSuTiemRepository.existsByMaTiem(lichSuTiem.getMaTiem())) {
            throw new IllegalArgumentException("Mã tiêm [" + lichSuTiem.getMaTiem() + "] đã tồn tại!");
        }
        return lichSuTiemRepository.save(lichSuTiem);
    }

    @Transactional
    public LichSuTiem updateLichSuTiem(Long id, LichSuTiem details) {
        LichSuTiem lichSuTiem = getLichSuTiemById(id);

        lichSuTiem.setIdVaccine(details.getIdVaccine());
        lichSuTiem.setIdLoVaccine(details.getIdLoVaccine());
        lichSuTiem.setSoMuiTiem(details.getSoMuiTiem());
        lichSuTiem.setNgayTiem(details.getNgayTiem());
        lichSuTiem.setTrangThai(details.getTrangThai());

        return lichSuTiemRepository.save(lichSuTiem);
    }

    @Transactional
    public void deleteLichSuTiem(Long id) {
        if (!lichSuTiemRepository.existsById(id)) {
            throw new EntityNotFoundException("Không tìm thấy lịch sử tiêm để xóa với ID: " + id);
        }
        lichSuTiemRepository.deleteById(id);
    }
}
