package com.vaxflow.invoice.repository;

import com.vaxflow.invoice.entity.HoaDonKhachHang;
import java.util.Optional;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HoaDonKhachHangRepository extends JpaRepository<HoaDonKhachHang, Long> {
    Optional<HoaDonKhachHang> findByMaHoaDon(String maHoaDon);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @org.springframework.data.jpa.repository.Query("select h from HoaDonKhachHang h where h.id = :id")
    Optional<HoaDonKhachHang> findByIdForUpdate(@Param("id") Long id);
}
