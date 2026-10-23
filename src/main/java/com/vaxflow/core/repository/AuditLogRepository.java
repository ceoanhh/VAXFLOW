package com.vaxflow.core.repository;

import com.vaxflow.core.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    List<AuditLog> findAllByOrderByThoiGianDesc();

    List<AuditLog> findTop200ByOrderByThoiGianDesc();

    List<AuditLog> findByTenDangNhapOrderByThoiGianDesc(String tenDangNhap);
}
