package com.vaxflow.customer.service;

import com.vaxflow.customer.entity.HoSo;
import com.vaxflow.customer.entity.KhachHang;
import com.vaxflow.customer.entity.LichSuTiem;
import com.vaxflow.customer.repository.HoSoRepository;
import com.vaxflow.customer.repository.KhachHangRepository;
import com.vaxflow.customer.repository.LichSuTiemRepository;
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
public class CustomerService {

    private final KhachHangRepository khachHangRepository;
    private final LichSuTiemRepository lichSuTiemRepository;
    private final HoSoRepository hoSoRepository;
    private final LoVacXinRepository loVacXinRepository;
    private final VacXinRepository vacXinRepository;

    public CustomerService(KhachHangRepository khachHangRepository, LichSuTiemRepository lichSuTiemRepository, HoSoRepository hoSoRepository, LoVacXinRepository loVacXinRepository, VacXinRepository vacXinRepository) {
        this.khachHangRepository = khachHangRepository;
        this.lichSuTiemRepository = lichSuTiemRepository;
        this.hoSoRepository = hoSoRepository;
        this.loVacXinRepository = loVacXinRepository;
        this.vacXinRepository = vacXinRepository;

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

    @Transactional
    public LichSuTiem saveLichSuTiem(LichSuTiem formInput, Long hoSoId, Long customerId) {

        // 1. Nếu là tạo mới
        if (formInput.getId() == null) {
            HoSo hoSo = hoSoRepository.findById(hoSoId).orElseThrow();
            KhachHang customer = khachHangRepository.findById(customerId).orElseThrow();

            formInput.setHoSo(hoSo);
            formInput.setKhachHang(customer);

            if (!"Hủy".equalsIgnoreCase(formInput.getTrangThai())) {
                Long newLoId = formInput.getLoVacXin().getId();
                int rows = loVacXinRepository.decreaseStock(newLoId, 1);
                if (rows == 0) throw new IllegalStateException("Lô vắc xin đã hết hàng!");
            }
            return lichSuTiemRepository.save(formInput);
        }

        // 2. Nếu là Chỉnh sửa: Lấy bản ghi GỐC từ Database ra trước (để lấy oldLoId chính xác)
        LichSuTiem oldRecord = lichSuTiemRepository.findById(formInput.getId())
                .orElseThrow(() -> new IllegalArgumentException("Bản ghi không tồn tại"));

        Long oldLoId = oldRecord.getLoVacXin() != null ? oldRecord.getLoVacXin().getId() : null;
        Long newLoId = formInput.getLoVacXin() != null ? formInput.getLoVacXin().getId() : null;

        String oldStatus = oldRecord.getTrangThai();
        String newStatus = formInput.getTrangThai();

        boolean isOldActive = !"Hủy".equalsIgnoreCase(oldStatus);
        boolean isNewActive = !"Hủy".equalsIgnoreCase(newStatus);

        // TH 1: Đổi Lô Vắc-xin khi cả 2 trạng thái đều Đang tiêm/Hoàn thành
        if (isOldActive && isNewActive && oldLoId != null && newLoId != null && !oldLoId.equals(newLoId)) {
            // Cộng trả 1 liều cho lô cũ
            loVacXinRepository.increaseStock(oldLoId, 1);

            // Trừ 1 liều ở lô mới
            int updatedRows = loVacXinRepository.decreaseStock(newLoId, 1);
            if (updatedRows == 0) {
                // Rollback lại kho lô cũ nếu lô mới không đủ tồn
                loVacXinRepository.decreaseStock(oldLoId, 1);
                throw new IllegalStateException("Lô vắc xin mới được chọn đã hết hàng!");
            }
        }
        // TH 2: Chuyển từ Đang tiêm -> HỦY
        else if (isOldActive && !isNewActive && oldLoId != null) {
            loVacXinRepository.increaseStock(oldLoId, 1);
        }
        // TH 3: Chuyển từ HỦY -> Đang tiêm
        else if (!isOldActive && isNewActive && newLoId != null) {
            int updatedRows = loVacXinRepository.decreaseStock(newLoId, 1);
            if (updatedRows == 0) {
                throw new IllegalStateException("Lô vắc xin không đủ tồn kho để khôi phục!");
            }
        }

        // Cập nhật các trường thông tin mới từ formInput vào oldRecord
        oldRecord.setMaTiem(formInput.getMaTiem());

        // Load đối tượng VacXin và LoVacXin thực tế từ DB để gán vào
        if (newLoId != null) {
            LoVacXin newLo = loVacXinRepository.findById(newLoId).orElseThrow();
            oldRecord.setLoVacXin(newLo);
        }
        if (formInput.getVacXin() != null && formInput.getVacXin().getId() != null) {
            VacXin newVacXin = vacXinRepository.findById(formInput.getVacXin().getId()).orElseThrow();
            oldRecord.setVacXin(newVacXin);
        }

        oldRecord.setSoMuiTiem(formInput.getSoMuiTiem());
        oldRecord.setNgayTiem(formInput.getNgayTiem());
        oldRecord.setTrangThai(formInput.getTrangThai());

        return lichSuTiemRepository.save(oldRecord);
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

    public Optional<HoSo> findHoSoByMaHoSo(String maHoSo) {
        return hoSoRepository.findByMaHoSo(maHoSo);
    }

    public Optional<LichSuTiem> findLichSuTiemByMaTiem(String maTiem) {
        return lichSuTiemRepository.findByMaTiem(maTiem);
    }
}
