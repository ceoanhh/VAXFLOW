package com.vaxflow.core.controller;

import com.vaxflow.core.entity.LichSuTiem;
import com.vaxflow.core.service.LichSuTiemService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/lich-su-tiem")
public class LichSuTiemController {

    private final LichSuTiemService lichSuTiemService;

    public LichSuTiemController(LichSuTiemService lichSuTiemService) {
        this.lichSuTiemService = lichSuTiemService;
    }

    @GetMapping
    public ResponseEntity<Page<LichSuTiem>> getAllLichSuTiem(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(lichSuTiemService.getAllLichSuTiem(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LichSuTiem> getLichSuTiemById(@PathVariable Long id) {
        return ResponseEntity.ok(lichSuTiemService.getLichSuTiemById(id));
    }

    @GetMapping("/ma/{maTiem}")
    public ResponseEntity<LichSuTiem> getLichSuTiemByMa(@PathVariable String maTiem) {
        return ResponseEntity.ok(lichSuTiemService.getLichSuTiemByMa(maTiem));
    }

    @GetMapping("/khach-hang/{idKhachHang}")
    public ResponseEntity<Page<LichSuTiem>> getLichSuTiemByKhachHang(
            @PathVariable Long idKhachHang,
            @PageableDefault(size = 10, sort = "ngayTiem", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(lichSuTiemService.getLichSuTiemByKhachHang(idKhachHang, pageable));
    }

    @GetMapping("/vaccine/{idVaccine}")
    public ResponseEntity<List<LichSuTiem>> getLichSuTiemByVaccine(@PathVariable Long idVaccine) {
        return ResponseEntity.ok(lichSuTiemService.getLichSuTiemByVaccine(idVaccine));
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<LichSuTiem>> getLichSuTiemTrongKhoangNgay(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(lichSuTiemService.getLichSuTiemTrongKhoangNgay(startDate, endDate));
    }

    @PostMapping
    public ResponseEntity<LichSuTiem> createLichSuTiem(@Valid @RequestBody LichSuTiem lichSuTiem) {
        LichSuTiem created = lichSuTiemService.createLichSuTiem(lichSuTiem);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LichSuTiem> updateLichSuTiem(
            @PathVariable Long id,
            @Valid @RequestBody LichSuTiem details) {
        return ResponseEntity.ok(lichSuTiemService.updateLichSuTiem(id, details));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLichSuTiem(@PathVariable Long id) {
        lichSuTiemService.deleteLichSuTiem(id);
        return ResponseEntity.noContent().build();
    }
}