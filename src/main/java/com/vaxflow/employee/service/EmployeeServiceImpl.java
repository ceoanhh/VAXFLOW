package com.vaxflow.employee.service;

import com.vaxflow.employee.dto.EmployeeDTO;
import com.vaxflow.employee.entity.Department;
import com.vaxflow.employee.entity.Employee;
import com.vaxflow.employee.repository.DepartmentRepository;
import com.vaxflow.employee.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public List<EmployeeDTO> getAll() {
        return employeeRepository.findAll().stream().map(this::toDTO).toList();
    }

    @Override
    public EmployeeDTO getById(Long id) {
        return toDTO(findEntityById(id));
    }

    @Override
    public EmployeeDTO create(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        copyToEntity(employeeDTO, employee);
        return toDTO(employeeRepository.save(employee));
    }

    @Override
    public EmployeeDTO update(Long id, EmployeeDTO employeeDTO) {
        Employee employee = findEntityById(id);
        copyToEntity(employeeDTO, employee);
        return toDTO(employeeRepository.save(employee));
    }

    @Override
    public void delete(Long id) {
        employeeRepository.delete(findEntityById(id));
    }

    @Override
    public Optional<EmployeeDTO> findByMaNhanSu(String maNhanSu) {
        return employeeRepository.findByMaNhanSu(maNhanSu).map(this::toDTO);
    }

    @Override
    public boolean existsByMaNhanSu(String maNhanSu) {
        return employeeRepository.existsByMaNhanSu(maNhanSu);
    }

    private Employee findEntityById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Khong tim thay nhan su voi id: " + id));
    }

    private Department findDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Khong tim thay phong ban voi id: " + id));
    }

    private void copyToEntity(EmployeeDTO dto, Employee entity) {
        entity.setMaNhanSu(dto.getMaNhanSu());
        entity.setHoVaTen(dto.getHoVaTen());
        entity.setCccd(dto.getCccd());
        entity.setNgaySinh(dto.getNgaySinh());
        entity.setGioiTinh(dto.getGioiTinh());
        entity.setDiaChi(dto.getDiaChi());
        entity.setSdt(dto.getSdt());
        entity.setEmail(dto.getEmail());
        entity.setChucVu(dto.getChucVu());
        entity.setNgayVaoLam(dto.getNgayVaoLam());
        entity.setLuongCung(dto.getLuongCung());
        entity.setPhongBan(findDepartmentById(dto.getIdPhongBan()));
    }

    private EmployeeDTO toDTO(Employee entity) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(entity.getId());
        dto.setMaNhanSu(entity.getMaNhanSu());
        dto.setHoVaTen(entity.getHoVaTen());
        dto.setCccd(entity.getCccd());
        dto.setNgaySinh(entity.getNgaySinh());
        dto.setGioiTinh(entity.getGioiTinh());
        dto.setDiaChi(entity.getDiaChi());
        dto.setSdt(entity.getSdt());
        dto.setEmail(entity.getEmail());
        dto.setChucVu(entity.getChucVu());
        dto.setNgayVaoLam(entity.getNgayVaoLam());
        dto.setLuongCung(entity.getLuongCung());
        dto.setIdPhongBan(entity.getPhongBan() != null ? entity.getPhongBan().getId() : null);
        dto.setTenPhongBan(entity.getPhongBan() != null ? entity.getPhongBan().getTen() : null);
        dto.setThoiGianTao(entity.getThoiGianTao());
        dto.setThoiGianCapNhat(entity.getThoiGianCapNhat());
        return dto;
    }
}
