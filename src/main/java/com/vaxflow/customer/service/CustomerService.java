package com.vaxflow.customer.service;

import com.vaxflow.customer.entity.HoSo;
import com.vaxflow.customer.entity.KhachHang;
import com.vaxflow.customer.entity.LichSuTiem;
import com.vaxflow.customer.repository.HoSoRepository;
import com.vaxflow.customer.repository.KhachHangRepository;
import com.vaxflow.customer.repository.LichSuTiemRepository;
import com.vaxflow.vaccine.repository.LoVacXinRepository;
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
    private final LoVacXinRepository loVacXinRepository;

    public CustomerService(KhachHangRepository khachHangRepository, LichSuTiemRepository lichSuTiemRepository, HoSoRepository hoSoRepository, LoVacXinRepository loVacXinRepository) {
        this.khachHangRepository = khachHangRepository;
        this.lichSuTiemRepository = lichSuTiemRepository;
        this.hoSoRepository = hoSoRepository;
        this.loVacXinRepository = loVacXinRepository;

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

    public void deleteLichSuTiemById(Long id) {
        lichSuTiemRepository.deleteById(id);
    }

    public LichSuTiem saveLichSuTiem(LichSuTiem recordToSave) {
        if (recordToSave.getId() == null) {
            // === THÀNH PHẦN 1: TẠO MỚI ===
            // Nếu tạo mới và trạng thái không phải Hủy -> Trừ 1 tồn kho
            if (!"Hủy".equalsIgnoreCase(recordToSave.getTrangThai())) {
                Long loId = recordToSave.getLoVacXin().getId();
                int updatedRows = loVacXinRepository.decreaseStock(loId, 1);
                if (updatedRows == 0) {
                    throw new IllegalStateException("Lô vắc xin đã hết hàng hoặc không khả dụng!");
                }
            }
        } else {
            // === THÀNH PHẦN 2: CẬP NHẬT TRẠNG THÁI ===
            LichSuTiem oldRecord = lichSuTiemRepository.findById(recordToSave.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Bản ghi tiêm không tồn tại"));

            String oldStatus = oldRecord.getTrangThai();
            String newStatus = recordToSave.getTrangThai();

            // TH 2a: Chuyển từ Đã tiêm/Hoàn thành -> Hủy => HOÀN TỒN KHO (+1)
            if (!"Hủy".equalsIgnoreCase(oldStatus) && "Hủy".equalsIgnoreCase(newStatus)) {
                if (oldRecord.getLoVacXin() != null) {
                    loVacXinRepository.increaseStock(oldRecord.getLoVacXin().getId(), 1);
                }
            }
            // TH 2b: Chuyển từ Hủy -> Đã tiêm/Hoàn thành => TRỪ TỒN KHO LẠI (-1)
            else if ("Hủy".equalsIgnoreCase(oldStatus) && !"Hủy".equalsIgnoreCase(newStatus)) {
                Long loId = recordToSave.getLoVacXin().getId();
                int updatedRows = loVacXinRepository.decreaseStock(loId, 1);
                if (updatedRows == 0) {
                    throw new IllegalStateException("Lô vắc xin không đủ tồn kho để khôi phục trạng thái tiêm!");
                }
            }
        }

        return lichSuTiemRepository.save(recordToSave);
    }

    /**
     * Xóa Lịch sử tiêm
     */
    public void deleteLichSuTiem(Long lichSuId) {
        LichSuTiem record = lichSuTiemRepository.findById(lichSuId)
                .orElseThrow(() -> new IllegalArgumentException("Bản ghi không tồn tại"));

        // Chỉ hoàn lại kho nếu trước đó mũi tiêm CHƯA BỊ HỦY
        if (!"Hủy".equalsIgnoreCase(record.getTrangThai()) && record.getLoVacXin() != null) {
            loVacXinRepository.increaseStock(record.getLoVacXin().getId(), 1);
        }

        lichSuTiemRepository.delete(record);
    }
}
