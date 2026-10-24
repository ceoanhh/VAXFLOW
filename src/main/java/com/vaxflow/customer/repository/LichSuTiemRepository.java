package com.vaxflow.customer.repository;

import com.vaxflow.customer.entity.LichSuTiem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LichSuTiemRepository extends JpaRepository<LichSuTiem, Long> {

    Optional<LichSuTiem> findByMaTiem(String maTiem);
}
