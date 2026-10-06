package com.vaxflow.employee.controller;

import com.vaxflow.employee.dto.AttendanceDTO;
import com.vaxflow.employee.service.AttendanceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/attendances")
@CrossOrigin("*")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @GetMapping
    public ResponseEntity<List<AttendanceDTO>> getAll() {
        return ResponseEntity.ok(attendanceService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttendanceDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(attendanceService.getById(id));
    }

    @PostMapping
    public ResponseEntity<AttendanceDTO> create(@RequestBody AttendanceDTO attendanceDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(attendanceService.create(attendanceDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AttendanceDTO> update(@PathVariable Long id, @RequestBody AttendanceDTO attendanceDTO) {
        return ResponseEntity.ok(attendanceService.update(id, attendanceDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        attendanceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
