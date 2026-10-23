package com.vaxflow.core.entity;

/**
 * Danh muc vai tro phan quyen theo chuc danh y te & quan tri
 */
public enum Role {
    ROLE_ADMIN("Quản trị viên hệ thống"),
    ROLE_DOCTOR("Bác sĩ khám sàng lọc"),
    ROLE_NURSE("Điều dưỡng tiêm chủng"),
    ROLE_STAFF("Nhân viên y tế / Thu ngân"),
    ROLE_MANAGER("Quản lý điều hành");

    private final String moTa;

    Role(String moTa) {
        this.moTa = moTa;
    }

    public String getMoTa() {
        return moTa;
    }
}
