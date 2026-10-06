package com.vaxflow.core.service;

import com.vaxflow.core.HoSoRequestDTO;
import com.vaxflow.core.HoSoResponseDTO;
import com.vaxflow.core.entity.HoSo;
import com.vaxflow.core.entity.LichSuTiem;
import com.vaxflow.core.exception.DuplicateResourceException;
import com.vaxflow.core.exception.ResourceNotFoundException;
import com.vaxflow.core.repository.HoSoRepository;
import com.vaxflow.core.repository.LichSuTiemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class HoSoService {

    private final HoSoRepository hoSoRepository;
    private final LichSuTiemRepository lichSuTiemRepository; // Injected để validate FK

    public HoSoService(HoSoRepository hoSoRepository, LichSuTiemRepository lichSuTiemRepository) {
        this.hoSoRepository = hoSoRepository;
        this.lichSuTiemRepository = lichSuTiemRepository;
    }

    public Page<HoSoResponseDTO> getAllHoSo(Pageable pageable) {
        return hoSoRepository.findAll(pageable).map(this::convertToDTO);
    }

    public HoSoResponseDTO getHoSoById(Long id) {
        HoSo hoSo = hoSoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy hồ sơ với ID: " + id));
        return convertToDTO(hoSo);
    }

    public HoSoResponseDTO getHoSoByMaHoSo(String maHoSo) {
        HoSo hoSo = hoSoRepository.findByMaHoSo(maHoSo)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy hồ sơ với mã: " + maHoSo));
        return convertToDTO(hoSo);
    }

    public Page<HoSoResponseDTO> getHoSoByBacSiKham(Long idBacSiKham, Pageable pageable) {
        return hoSoRepository.findByIdBacSiKham(idBacSiKham, pageable).map(this::convertToDTO);
    }

    public List<HoSoResponseDTO> searchByGhiChu(String keyword) {
        return hoSoRepository.findByGhiChuTheoDoiContainingIgnoreCase(keyword)
                .stream().map(this::convertToDTO).toList();
    }

    public List<HoSoResponseDTO> getHoSoTrongKhoangThoiGian(LocalDateTime startDate, LocalDateTime endDate) {
        return hoSoRepository.findByThoiGianTaoBetween(startDate, endDate)
                .stream().map(this::convertToDTO).toList();
    }

    @Transactional
    public HoSoResponseDTO createHoSo(HoSoRequestDTO dto) {
        if (hoSoRepository.existsByMaHoSo(dto.getMaHoSo())) {
            throw new DuplicateResourceException("Mã hồ sơ đã tồn tại trên hệ thống!");
        }

        LichSuTiem lichSuTiem = lichSuTiemRepository.findById(dto.getIdLichSuTiem())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy Lịch Sử Tiêm ID: " + dto.getIdLichSuTiem()));

        HoSo hoSo = new HoSo();
        mapDTOToEntity(dto, hoSo);
        hoSo.setLichSuTiem(lichSuTiem);

        HoSo savedHoSo = hoSoRepository.save(hoSo);
        return convertToDTO(savedHoSo);
    }

    @Transactional
    public HoSoResponseDTO updateHoSo(Long id, HoSoRequestDTO dto) {
        HoSo hoSo = hoSoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy hồ sơ với ID: " + id));

        LichSuTiem lichSuTiem = lichSuTiemRepository.findById(dto.getIdLichSuTiem())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy Lịch Sử Tiêm ID: " + dto.getIdLichSuTiem()));

        mapDTOToEntity(dto, hoSo);
        hoSo.setLichSuTiem(lichSuTiem);

        return convertToDTO(hoSoRepository.save(hoSo));
    }

    @Transactional
    public void deleteHoSo(Long id) {
        if (!hoSoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Không tìm thấy hồ sơ để xóa với ID: " + id);
        }
        hoSoRepository.deleteById(id);
    }

    // Helper methods (Có thể dùng MapStruct thay thế)
    private HoSoResponseDTO convertToDTO(HoSo hoSo) {
        HoSoResponseDTO dto = new HoSoResponseDTO();
        dto.setId(hoSo.getId());
        dto.setMaHoSo(hoSo.getMaHoSo());
        dto.setIdBacSiTiem(hoSo.getIdBacSiTiem());
        dto.setIdBacSiKham(hoSo.getIdBacSiKham());
        dto.setIdDieuDuong(hoSo.getIdDieuDuong());
        dto.setIdTiepDon(hoSo.getIdTiepDon());
        dto.setGhiChuTheoDoi(hoSo.getGhiChuTheoDoi());
        dto.setThoiGianTao(hoSo.getThoiGianTao());
        if (hoSo.getLichSuTiem() != null) {
            dto.setIdLichSuTiem(hoSo.getLichSuTiem().getId());
        }
        return dto;
    }

    private void mapDTOToEntity(HoSoRequestDTO dto, HoSo hoSo) {
        hoSo.setMaHoSo(dto.getMaHoSo());
        hoSo.setIdBacSiTiem(dto.getIdBacSiTiem());
        hoSo.setIdBacSiKham(dto.getIdBacSiKham());
        hoSo.setIdDieuDuong(dto.getIdDieuDuong());
        hoSo.setIdTiepDon(dto.getIdTiepDon());
        hoSo.setGhiChuTheoDoi(dto.getGhiChuTheoDoi());
    }
}