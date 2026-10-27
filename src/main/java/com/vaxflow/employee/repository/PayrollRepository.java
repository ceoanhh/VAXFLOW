package com.vaxflow.employee.repository;

import com.vaxflow.employee.entity.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PayrollRepository extends JpaRepository<Payroll, Long> {
    
    Payroll findFirstByNhanSuIdOrderByNamDescThangDesc(Long idNhanSu);

    List<Payroll> findByNhanSuIdOrderByNamDescThangDesc (Long idNhanSu);

    @Transactional
    void deleteAllByNhanSuId(Long idNhanSu);
}
