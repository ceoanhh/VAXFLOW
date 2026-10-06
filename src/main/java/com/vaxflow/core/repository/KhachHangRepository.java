package com.vaxflow.core.repository;

import com.vaxflow.core.entity.KhachHang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang, Long> {

    // Find by unique customer code
    Optional<KhachHang> findByMaKhachHang(String maKhachHang);

    boolean existsByMaKhachHang(String maKhachHang);

    boolean existsBySdt(String sdt);

    // Unified single-keyword search for Name OR Phone Number (Paginated)
    @Query("SELECT k FROM KhachHang k WHERE " +
            "LOWER(k.tenKhachHang) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "k.sdt LIKE CONCAT('%', :keyword, '%')")
    Page<KhachHang> searchByNameOrPhone(@Param("keyword") String keyword, Pageable pageable);

}
