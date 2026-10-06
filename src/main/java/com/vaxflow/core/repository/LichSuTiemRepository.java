package com.vaxflow.core.repository;

import com.vaxflow.core.entity.LichSuTiem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface LichSuTiemRepository extends JpaRepository<LichSuTiem, Long> {

    @Override
    @EntityGraph(attributePaths = {"hoSoList"})
    List<LichSuTiem> findAll();

    @EntityGraph(attributePaths = {"hoSoList"})
    Optional<LichSuTiem> findByMaTiem(String maTiem);

    boolean existsByMaTiem(String maTiem);

    // Dùng DISTINCT để tránh duplicate khi JOIN với N-N
    @Query("SELECT DISTINCT l FROM LichSuTiem l JOIN l.khachHangSet k WHERE k.id = :idKhachHang")
    Page<LichSuTiem> findByKhachHangId(@Param("idKhachHang") Long idKhachHang, Pageable pageable);

    List<LichSuTiem> findByIdVaccine(Long idVaccine);

    @EntityGraph(attributePaths = {"hoSoList"})
    List<LichSuTiem> findByNgayTiemBetween(LocalDate startDate, LocalDate endDate);
}