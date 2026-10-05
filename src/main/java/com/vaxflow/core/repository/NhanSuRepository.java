package com.vaxflow.core.repository;

import com.vaxflow.core.entity.NhanSu;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NhanSuRepository extends JpaRepository<NhanSu, Long> {

    Optional<NhanSu> findByMaNhanSu(String maNhanSu);
}
