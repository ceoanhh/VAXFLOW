package com.vaxflow.core.repository;

import com.vaxflow.core.entity.LichSuTiem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LichSuTiemRepository extends JpaRepository<LichSuTiem, Long> {

    Optional<LichSuTiem> findByMaTiem(String maTiem);
}
