package com.vaxflow.employee.service;

import com.vaxflow.employee.dto.PayrollDTO;
import com.vaxflow.employee.entity.Employee;
import com.vaxflow.employee.entity.Payroll;
import com.vaxflow.employee.repository.EmployeeRepository;
import com.vaxflow.employee.repository.PayrollRepository;
import jakarta.persistence.EntityNotFoundException;

import java.math.BigDecimal;
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
    public PayrollDTO create(PayrollDTO payrollDTO) {
        Payroll payroll = new Payroll();
        copyToEntity(payrollDTO, payroll);
        BigDecimal luong = payroll.getLuongCung() != null ? payroll.getLuongCung() : BigDecimal.ZERO;
        BigDecimal thuong = payroll.getThuong() != null ? payroll.getThuong() : BigDecimal.ZERO;
        BigDecimal khauTru = payroll.getKhauTru() != null ? payroll.getKhauTru() : BigDecimal.ZERO;
        payroll.setThucNhan(luong.add(thuong).subtract(khauTru));
        String maNV = payroll.getNhanSu().getMaNhanSu();
        String nam_format = String.valueOf(payroll.getNam());
        String thang_format = String.format("%02d", payroll.getThang());
        payroll.setMaBangLuong(maNV + nam_format + thang_format);
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

    public List<PayrollDTO> getPayrollsByNhanSuId(Long nhanSuId) {
        // Gọi thẳng truy vấn tối ưu từ Repository đã viết sẵn
        return payrollRepository.findByNhanSuIdOrderByNamDescThangDesc(nhanSuId)
                .stream()
                .map(this::toDTO) // Chuyển đổi từ dữ liệu thô (Entity) sang DTO để trả về
                .toList();
    }

    private void copyToEntity(PayrollDTO dto, Payroll entity) {
        entity.setNhanSu(findEmployeeById(dto.getIdNhanSu()));
        entity.setMaBangLuong(dto.getMaBangLuong());
        entity.setThang(dto.getThang());
        entity.setNam(dto.getNam());
        entity.setLuongCung(dto.getLuongCung());
        entity.setThuong(dto.getThuong());
        entity.setLyDoThuong(dto.getLyDoThuong());
        entity.setKhauTru(dto.getKhauTru());
        entity.setLyDoKhauTru(dto.getLyDoKhauTru());
        entity.setThucNhan(dto.getThucNhan());
        
    }

    private PayrollDTO toDTO(Payroll entity) {
        PayrollDTO dto = new PayrollDTO();
        dto.setId(entity.getId());
        dto.setIdNhanSu(entity.getNhanSu() != null ? entity.getNhanSu().getId() : null);
        dto.setMaBangLuong(entity.getMaBangLuong());
        dto.setThang(entity.getThang());
        dto.setNam(entity.getNam());
        dto.setLuongCung(entity.getLuongCung());
        dto.setThuong(entity.getThuong());
        dto.setLyDoThuong(entity.getLyDoThuong());
        dto.setKhauTru(entity.getKhauTru());
        dto.setLyDoKhauTru(entity.getLyDoKhauTru());
        dto.setThucNhan(entity.getThucNhan());
        return dto;
    }
}
