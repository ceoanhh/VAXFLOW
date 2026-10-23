package com.vaxflow.vaccine.repository;

import com.vaxflow.vaccine.entity.VacXin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VacXinRepository extends JpaRepository<VacXin, Long> {
    boolean existsByMaVacXin(String maVacXin);
}
