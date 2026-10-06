package com.vaxflow.core.controller;

import com.vaxflow.core.KhachHangRequestDTO;
import com.vaxflow.core.KhachHangResponseDTO;
import com.vaxflow.core.service.KhachHangService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/khach-hang")
public class KhachHangController {

    private final KhachHangService khachHangService;

    public KhachHangController(KhachHangService khachHangService) {
        this.khachHangService = khachHangService;
    }

    @GetMapping
    public ResponseEntity<Page<KhachHangResponseDTO>> getAllKhachHang(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(khachHangService.getAllKhachHang(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<KhachHangResponseDTO> getKhachHangById(@PathVariable Long id) {
        return ResponseEntity.ok(khachHangService.getKhachHangById(id));
    }

    @GetMapping("/ma/{maKhachHang}")
    public ResponseEntity<KhachHangResponseDTO> getKhachHangByMa(@PathVariable String maKhachHang) {
        return ResponseEntity.ok(khachHangService.getKhachHangByMa(maKhachHang));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<KhachHangResponseDTO>> searchKhachHang(
            @RequestParam(required = false) String keyword,
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(khachHangService.searchKhachHang(keyword, pageable));
    }

    @PostMapping
    public ResponseEntity<KhachHangResponseDTO> createKhachHang(@Valid @RequestBody KhachHangRequestDTO request) {
        KhachHangResponseDTO created = khachHangService.createKhachHang(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<KhachHangResponseDTO> updateKhachHang(
            @PathVariable Long id,
            @Valid @RequestBody KhachHangRequestDTO request) {
        return ResponseEntity.ok(khachHangService.updateKhachHang(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKhachHang(@PathVariable Long id) {
        khachHangService.deleteKhachHang(id);
        return ResponseEntity.noContent().build();
    }
}