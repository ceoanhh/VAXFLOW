package com.vaxflow.core.service;

import com.vaxflow.core.KhachHangRequestDTO;
import com.vaxflow.core.KhachHangResponseDTO;
import com.vaxflow.core.entity.KhachHang;
import com.vaxflow.core.exception.DuplicateResourceException;
import com.vaxflow.core.exception.ResourceNotFoundException;
import com.vaxflow.core.repository.KhachHangRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class KhachHangService {

    private final KhachHangRepository khachHangRepository;

    public KhachHangService(KhachHangRepository khachHangRepository) {
        this.khachHangRepository = khachHangRepository;
    }

    public Page<KhachHangResponseDTO> getAllKhachHang(Pageable pageable) {
        return khachHangRepository.findAll(pageable)
                .map(KhachHangResponseDTO::fromEntity);
    }

    public KhachHangResponseDTO getKhachHangById(Long id) {
        KhachHang entity = khachHangRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy khách hàng với ID: " + id));
        return KhachHangResponseDTO.fromEntity(entity);
    }

    public KhachHangResponseDTO getKhachHangByMa(String maKhachHang) {
        KhachHang entity = khachHangRepository.findByMaKhachHang(maKhachHang)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy khách hàng với mã: " + maKhachHang));
        return KhachHangResponseDTO.fromEntity(entity);
    }

    public Page<KhachHangResponseDTO> searchKhachHang(String keyword, Pageable pageable) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllKhachHang(pageable);
        }
        return khachHangRepository.searchByNameOrPhone(keyword.trim(), pageable)
                .map(KhachHangResponseDTO::fromEntity);
    }

    @Transactional
    public KhachHangResponseDTO createKhachHang(KhachHangRequestDTO request) {
        if (khachHangRepository.existsByMaKhachHang(request.getMaKhachHang())) {
            throw new DuplicateResourceException("Mã khách hàng [" + request.getMaKhachHang() + "] đã tồn tại!");
        }

        if (khachHangRepository.existsBySdt(request.getSdt())) {
            throw new DuplicateResourceException("Số điện thoại [" + request.getSdt() + "] đã được sử dụng!");
        }

        KhachHang khachHang = new KhachHang();
        khachHang.setMaKhachHang(request.getMaKhachHang());
        khachHang.setTenKhachHang(request.getTenKhachHang());
        khachHang.setSdt(request.getSdt());
        khachHang.setNgaySinh(request.getNgaySinh());
        khachHang.setDiaChi(request.getDiaChi());

        KhachHang saved = khachHangRepository.save(khachHang);
        return KhachHangResponseDTO.fromEntity(saved);
    }

    @Transactional
    public KhachHangResponseDTO updateKhachHang(Long id, KhachHangRequestDTO request) {
        KhachHang khachHang = khachHangRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy khách hàng với ID: " + id));

        // Kiểm tra trùng SĐT nếu thay đổi
        if (!request.getSdt().equals(khachHang.getSdt())
                && khachHangRepository.existsBySdt(request.getSdt())) {
            throw new DuplicateResourceException("Số điện thoại [" + request.getSdt() + "] đã thuộc về khách hàng khác!");
        }

        khachHang.setTenKhachHang(request.getTenKhachHang());
        khachHang.setSdt(request.getSdt());
        khachHang.setNgaySinh(request.getNgaySinh());
        khachHang.setDiaChi(request.getDiaChi());

        return KhachHangResponseDTO.fromEntity(khachHang);
    }

    @Transactional
    public void deleteKhachHang(Long id) {
        if (!khachHangRepository.existsById(id)) {
            throw new ResourceNotFoundException("Không tìm thấy khách hàng để xóa với ID: " + id);
        }
        khachHangRepository.deleteById(id);
    }
}