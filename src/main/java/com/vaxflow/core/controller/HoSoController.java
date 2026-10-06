package com.vaxflow.core.controller;

import com.vaxflow.core.HoSoRequestDTO;
import com.vaxflow.core.HoSoResponseDTO;
import com.vaxflow.core.service.HoSoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/ho-so")
public class HoSoController {

    private final HoSoService hoSoService;

    public HoSoController(HoSoService hoSoService) {
        this.hoSoService = hoSoService;
    }

    @GetMapping
    public ResponseEntity<Page<HoSoResponseDTO>> getAllHoSo(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(hoSoService.getAllHoSo(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HoSoResponseDTO> getHoSoById(@PathVariable Long id) {
        return ResponseEntity.ok(hoSoService.getHoSoById(id));
    }

    @GetMapping("/ma/{maHoSo}")
    public ResponseEntity<HoSoResponseDTO> getHoSoByMaHoSo(@PathVariable String maHoSo) {
        return ResponseEntity.ok(hoSoService.getHoSoByMaHoSo(maHoSo));
    }

    @GetMapping("/bac-si-kham/{idBacSiKham}")
    public ResponseEntity<Page<HoSoResponseDTO>> getHoSoByBacSiKham(
            @PathVariable Long idBacSiKham,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(hoSoService.getHoSoByBacSiKham(idBacSiKham, pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<List<HoSoResponseDTO>> searchByGhiChu(@RequestParam String keyword) {
        return ResponseEntity.ok(hoSoService.searchByGhiChu(keyword));
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<HoSoResponseDTO>> getHoSoByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return ResponseEntity.ok(hoSoService.getHoSoTrongKhoangThoiGian(startDate, endDate));
    }

    @PostMapping
    public ResponseEntity<HoSoResponseDTO> createHoSo(@Valid @RequestBody HoSoRequestDTO requestDTO) {
        HoSoResponseDTO createdHoSo = hoSoService.createHoSo(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdHoSo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HoSoResponseDTO> updateHoSo(
            @PathVariable Long id,
            @Valid @RequestBody HoSoRequestDTO requestDTO) {
        return ResponseEntity.ok(hoSoService.updateHoSo(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHoSo(@PathVariable Long id) {
        hoSoService.deleteHoSo(id);
        return ResponseEntity.noContent().build();
    }
}