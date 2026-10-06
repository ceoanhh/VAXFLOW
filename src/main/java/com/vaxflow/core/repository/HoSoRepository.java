package com.vaxflow.core.repository;

import com.vaxflow.core.entity.HoSo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface HoSoRepository extends JpaRepository<HoSo, Long> {

    @Override
    @EntityGraph(attributePaths = {"lichSuTiem"})
    Page<HoSo> findAll(Pageable pageable);

    @Override
    @EntityGraph(attributePaths = {"lichSuTiem"})
    Optional<HoSo> findById(Long id);

    @EntityGraph(attributePaths = {"lichSuTiem"})
    Optional<HoSo> findByMaHoSo(String maHoSo);

    boolean existsByMaHoSo(String maHoSo);

    @EntityGraph(attributePaths = {"lichSuTiem"})
    Page<HoSo> findByIdBacSiKham(Long idBacSiKham, Pageable pageable);

    @EntityGraph(attributePaths = {"lichSuTiem"})
    List<HoSo> findByThoiGianTaoBetween(LocalDateTime startDate, LocalDateTime endDate);

    @EntityGraph(attributePaths = {"lichSuTiem"})
    List<HoSo> findByGhiChuTheoDoiContainingIgnoreCase(String keyword);
}