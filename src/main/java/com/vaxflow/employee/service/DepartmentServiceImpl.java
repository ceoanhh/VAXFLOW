package com.vaxflow.employee.service;

import com.vaxflow.employee.dto.DepartmentDTO;
import com.vaxflow.employee.entity.Department;
import com.vaxflow.employee.repository.DepartmentRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public List<DepartmentDTO> getAll() {
        return departmentRepository.findAll().stream().map(this::toDTO).toList();
    }

    @Override
    public DepartmentDTO getById(Long id) {
        return toDTO(findEntityById(id));
    }

    @Override
    public DepartmentDTO create(DepartmentDTO departmentDTO) {
        Department department = new Department();
        copyToEntity(departmentDTO, department);
        return toDTO(departmentRepository.save(department));
    }

    @Override
    public DepartmentDTO update(Long id, DepartmentDTO departmentDTO) {
        Department department = findEntityById(id);
        copyToEntity(departmentDTO, department);
        return toDTO(departmentRepository.save(department));
    }

    @Override
    public void delete(Long id) {
        departmentRepository.delete(findEntityById(id));
    }

    private Department findEntityById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Khong tim thay phong ban voi id: " + id));
    }

    private void copyToEntity(DepartmentDTO dto, Department entity) {
        entity.setTen(dto.getTen());
        entity.setGhiChu(dto.getGhiChu());
    }

    private DepartmentDTO toDTO(Department entity) {
        DepartmentDTO dto = new DepartmentDTO();
        dto.setId(entity.getId());
        dto.setTen(entity.getTen());
        dto.setGhiChu(entity.getGhiChu());
        dto.setThoiGianTao(entity.getThoiGianTao());
        dto.setThoiGianCapNhat(entity.getThoiGianCapNhat());
        return dto;
    }
}
