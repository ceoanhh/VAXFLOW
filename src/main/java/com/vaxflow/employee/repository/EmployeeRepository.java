package com.vaxflow.employee.repository;

import com.vaxflow.employee.entity.Employee;
import java.util.Optional;

import com.vaxflow.employee.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByMaNhanSu(String maNhanSu);

    boolean existsByMaNhanSu(String maNhanSu);
    boolean existsByCccd(String cccd);
    boolean existsBySdt(String sdt);
    boolean existsByEmail(String email);

    long countByPhongBanIdAndChucVu(Long phongBanId, Position chucVu);
}
