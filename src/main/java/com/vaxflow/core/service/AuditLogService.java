package com.vaxflow.core.service;

import com.vaxflow.core.entity.AuditLog;
import com.vaxflow.core.repository.AuditLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    public AuditLogService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    @Transactional
    public void log(String tenDangNhap, String hanhDong, String chiTiet, String diaChiIp) {
        AuditLog auditLog = new AuditLog(tenDangNhap, hanhDong, chiTiet, diaChiIp);
        auditLogRepository.save(auditLog);
    }

    @Transactional(readOnly = true)
    public List<AuditLog> getRecentLogs() {
        return auditLogRepository.findTop200ByOrderByThoiGianDesc();
    }
}
