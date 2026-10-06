package com.vaxflow.employee.service;

import com.vaxflow.employee.dto.AttendanceDTO;
import java.util.List;

public interface AttendanceService {

    List<AttendanceDTO> getAll();

    AttendanceDTO getById(Long id);

    AttendanceDTO create(AttendanceDTO attendanceDTO);

    AttendanceDTO update(Long id, AttendanceDTO attendanceDTO);

    void delete(Long id);
}
