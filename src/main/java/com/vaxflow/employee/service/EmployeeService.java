package com.vaxflow.employee.service;

import com.vaxflow.employee.dto.EmployeeDTO;
import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    List<EmployeeDTO> getAll();

    EmployeeDTO getById(Long id);

    EmployeeDTO create(EmployeeDTO employeeDTO);

    EmployeeDTO update(Long id, EmployeeDTO employeeDTO);

    void delete(Long id);

    Optional<EmployeeDTO> findByMaNhanSu(String maNhanSu);

    boolean existsByMaNhanSu(String maNhanSu);
}
