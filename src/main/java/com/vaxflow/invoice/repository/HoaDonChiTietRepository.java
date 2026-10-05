package com.vaxflow.invoice.repository;

import com.vaxflow.invoice.entity.HoaDonChiTiet;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HoaDonChiTietRepository extends JpaRepository<HoaDonChiTiet, Long> {
    boolean existsByIdTiem(Long idTiem);
    List<HoaDonChiTiet> findAllByIdHoaDonOrderByIdAsc(Long idHoaDon);
}
