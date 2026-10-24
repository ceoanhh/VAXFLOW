package com.vaxflow.customer.repository;

import com.vaxflow.customer.entity.HoSo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HoSoRepository extends JpaRepository<HoSo, Long> {

    Optional<HoSo> findByMaHoSo(String maHoSo);
}
