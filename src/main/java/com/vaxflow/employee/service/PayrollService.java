package com.vaxflow.employee.service;

import com.vaxflow.employee.dto.PayrollDTO;
import java.util.List;

public interface PayrollService {

    List<PayrollDTO> getAll();

    PayrollDTO create(PayrollDTO payrollDTO);

    void delete(Long id);

    List<PayrollDTO> getPayrollsByNhanSuId(Long nhanSuId);
}
