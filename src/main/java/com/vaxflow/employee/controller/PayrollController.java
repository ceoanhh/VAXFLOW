package com.vaxflow.employee.controller;

import com.vaxflow.employee.dto.PayrollDTO;
import com.vaxflow.employee.service.PayrollService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payrolls")
@CrossOrigin("*")
public class PayrollController {

    private final PayrollService payrollService;

    public PayrollController(PayrollService payrollService) {
        this.payrollService = payrollService;
    }

    @GetMapping
    public ResponseEntity<List<PayrollDTO>> getAll() {
        return ResponseEntity.ok(payrollService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PayrollDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(payrollService.getById(id));
    }

    @PostMapping
    public ResponseEntity<PayrollDTO> create(@RequestBody PayrollDTO payrollDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(payrollService.create(payrollDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PayrollDTO> update(@PathVariable Long id, @RequestBody PayrollDTO payrollDTO) {
        return ResponseEntity.ok(payrollService.update(id, payrollDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        payrollService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
