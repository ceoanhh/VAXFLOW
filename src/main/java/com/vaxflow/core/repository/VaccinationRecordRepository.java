package com.vaxflow.core.repository;

import com.vaxflow.core.entity.VaccinationRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VaccinationRecordRepository extends JpaRepository<VaccinationRecord, Long> {

    List<VaccinationRecord> findAllByOrderByNgayTiemDesc();

    List<VaccinationRecord> findByKhachHangIdOrderByNgayTiemDesc(Long khachHangId);

    @Query("SELECT v FROM VaccinationRecord v JOIN v.khachHang c WHERE " +
           "LOWER(c.hoTen) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
           "c.soDienThoai LIKE CONCAT('%', :kw, '%') OR " +
           "LOWER(v.tenVacXin) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
           "v.soLo LIKE CONCAT('%', :kw, '%') " +
           "ORDER BY v.ngayTiem DESC")
    List<VaccinationRecord> searchRecords(@Param("kw") String keyword);

    long countByTrangThai(String trangThai);

    long countByNgayTiem(LocalDate ngayTiem);
}
