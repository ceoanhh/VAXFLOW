package com.vaxflow.invoice.repository;

import com.vaxflow.invoice.entity.ThanhToan;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThanhToanRepository extends JpaRepository<ThanhToan, Long> {
    Optional<ThanhToan> findByIdHoaDon(Long idHoaDon);
}
