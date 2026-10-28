package com.vaxflow.employee.service;

import com.vaxflow.employee.dto.DepartmentDTO;
import java.util.List;

public interface DepartmentService {

    List<DepartmentDTO> getAll();

    DepartmentDTO getById(Long id);
}
