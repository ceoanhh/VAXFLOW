package com.vaxflow.employee.service;

import com.vaxflow.employee.dto.AttendanceDTO;
import com.vaxflow.employee.entity.Attendance;
import com.vaxflow.employee.entity.Employee;
import com.vaxflow.employee.repository.AttendanceRepository;
import com.vaxflow.employee.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;

    public AttendanceServiceImpl(AttendanceRepository attendanceRepository, EmployeeRepository employeeRepository) {
        this.attendanceRepository = attendanceRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<AttendanceDTO> getAll() {
        return attendanceRepository.findAll().stream().map(this::toDTO).toList();
    }

    @Override
    public AttendanceDTO getById(Long id) {
        return toDTO(findEntityById(id));
    }

    @Override
    public AttendanceDTO create(AttendanceDTO attendanceDTO) {
        Attendance attendance = new Attendance();
        copyToEntity(attendanceDTO, attendance);
        return toDTO(attendanceRepository.save(attendance));
    }

    @Override
    public AttendanceDTO update(Long id, AttendanceDTO attendanceDTO) {
        Attendance attendance = findEntityById(id);
        copyToEntity(attendanceDTO, attendance);
        return toDTO(attendanceRepository.save(attendance));
    }

    @Override
    public void delete(Long id) {
        attendanceRepository.delete(findEntityById(id));
    }

    private Attendance findEntityById(Long id) {
        return attendanceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Khong tim thay cham cong voi id: " + id));
    }

    private Employee findEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Khong tim thay nhan su voi id: " + id));
    }

    private void copyToEntity(AttendanceDTO dto, Attendance entity) {
        entity.setNhanSu(findEmployeeById(dto.getIdNhanSu()));
        entity.setNgay(dto.getNgay());
        entity.setGioVao(dto.getGioVao());
        entity.setGioRa(dto.getGioRa());
        entity.setTrangThai(dto.getTrangThai());
    }

    private AttendanceDTO toDTO(Attendance entity) {
        AttendanceDTO dto = new AttendanceDTO();
        dto.setId(entity.getId());
        dto.setIdNhanSu(entity.getNhanSu() != null ? entity.getNhanSu().getId() : null);
        dto.setNgay(entity.getNgay());
        dto.setGioVao(entity.getGioVao());
        dto.setGioRa(entity.getGioRa());
        dto.setTrangThai(entity.getTrangThai());
        return dto;
    }
}
