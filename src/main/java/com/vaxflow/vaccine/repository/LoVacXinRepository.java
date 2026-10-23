package com.vaxflow.vaccine.repository;

import com.vaxflow.vaccine.entity.LoVacXin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LoVacXinRepository extends JpaRepository<LoVacXin, Long> {

    @Query("SELECT l FROM LoVacXin l WHERE l.vacXin.id = :idVacXin AND l.soLuongConLai > 0 AND l.hanSuDung >= CURRENT_DATE ORDER BY l.hanSuDung ASC, l.ngayNhap ASC")
    List<LoVacXin> findAvailableBatchesForFIFO(@Param("idVacXin") Long idVacXin);

    @Query("SELECT SUM(l.soLuongConLai) FROM LoVacXin l WHERE l.vacXin.id = :idVacXin AND l.hanSuDung >= CURRENT_DATE")
    Integer getTotalAvailableStock(@Param("idVacXin") Long idVacXin);
}
