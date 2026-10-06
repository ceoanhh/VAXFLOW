package com.vaxflow.core.repository;

import com.vaxflow.core.entity.Employee;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByMaNhanSu(String maNhanSu);
}
