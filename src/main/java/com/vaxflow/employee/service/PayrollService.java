package com.vaxflow.employee.service;

import com.vaxflow.employee.dto.PayrollDTO;
import java.util.List;

public interface PayrollService {

    List<PayrollDTO> getAll();

    PayrollDTO getById(Long id);

    PayrollDTO create(PayrollDTO payrollDTO);

    PayrollDTO update(Long id, PayrollDTO payrollDTO);

    void delete(Long id);
}
