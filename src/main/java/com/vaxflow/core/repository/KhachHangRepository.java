package com.vaxflow.core.repository;

import com.vaxflow.core.entity.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang, Long> {

    Optional<KhachHang> findByMaKhachHang(String maKhachHang);

    boolean existsByMaKhachHang(String maKhachHang);

    List<KhachHang> findByTenKhachHangContainingIgnoreCaseOrSdtContaining(String tenKhachHang, String sdt);
}
