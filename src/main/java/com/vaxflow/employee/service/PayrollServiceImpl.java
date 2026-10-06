package com.vaxflow.employee.service;

import com.vaxflow.employee.dto.PayrollDTO;
import com.vaxflow.employee.entity.Employee;
import com.vaxflow.employee.entity.Payroll;
import com.vaxflow.employee.repository.EmployeeRepository;
import com.vaxflow.employee.repository.PayrollRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PayrollServiceImpl implements PayrollService {

    private final PayrollRepository payrollRepository;
    private final EmployeeRepository employeeRepository;

    public PayrollServiceImpl(PayrollRepository payrollRepository, EmployeeRepository employeeRepository) {
        this.payrollRepository = payrollRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<PayrollDTO> getAll() {
        return payrollRepository.findAll().stream().map(this::toDTO).toList();
    }

    @Override
    public PayrollDTO getById(Long id) {
        return toDTO(findEntityById(id));
    }

    @Override
    public PayrollDTO create(PayrollDTO payrollDTO) {
        Payroll payroll = new Payroll();
        copyToEntity(payrollDTO, payroll);
        return toDTO(payrollRepository.save(payroll));
    }

    @Override
    public PayrollDTO update(Long id, PayrollDTO payrollDTO) {
        Payroll payroll = findEntityById(id);
        copyToEntity(payrollDTO, payroll);
        return toDTO(payrollRepository.save(payroll));
    }

    @Override
    public void delete(Long id) {
        payrollRepository.delete(findEntityById(id));
    }

    private Payroll findEntityById(Long id) {
        return payrollRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Khong tim thay bang luong voi id: " + id));
    }

    private Employee findEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Khong tim thay nhan su voi id: " + id));
    }

    private void copyToEntity(PayrollDTO dto, Payroll entity) {
        entity.setNhanSu(findEmployeeById(dto.getIdNhanSu()));
        entity.setThang(dto.getThang());
        entity.setNam(dto.getNam());
        entity.setLuongCung(dto.getLuongCung());
        entity.setThuong(dto.getThuong());
        entity.setKhauTru(dto.getKhauTru());
        entity.setThucNhan(dto.getThucNhan());
    }

    private PayrollDTO toDTO(Payroll entity) {
        PayrollDTO dto = new PayrollDTO();
        dto.setId(entity.getId());
        dto.setIdNhanSu(entity.getNhanSu() != null ? entity.getNhanSu().getId() : null);
        dto.setThang(entity.getThang());
        dto.setNam(entity.getNam());
        dto.setLuongCung(entity.getLuongCung());
        dto.setThuong(entity.getThuong());
        dto.setKhauTru(entity.getKhauTru());
        dto.setThucNhan(entity.getThucNhan());
        return dto;
    }
}
