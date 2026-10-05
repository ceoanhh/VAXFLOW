package com.vaxflow.core.repository;

import com.vaxflow.core.entity.BangLuong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BangLuongRepository extends JpaRepository<BangLuong, Long> {
}
