package com.vaxflow.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

/**
 * Nhat ky hoat dong he thong (Audit Log)
 * Ghi lai cac hanh vi quan trong: Dang nhap, dang nhap that bai, khoa tai khoan,
 * doi mat khau, cap tai khoan, thao tac quan tri.
 */
@Entity
@Table(name = "nhat_ky_hoat_dong")
public class AuditLog extends BaseEntity {

    @Column(name = "thoi_gian", nullable = false)
    private LocalDateTime thoiGian;

    @Column(name = "ten_dang_nhap", length = 50)
    private String tenDangNhap;

    @Column(name = "hanh_dong", nullable = false, length = 100)
    private String hanhDong;

    @Column(name = "chi_tiet", length = 1000)
    private String chiTiet;

    @Column(name = "dia_chi_ip", length = 50)
    private String diaChiIp;

    public AuditLog() {
    }

    public AuditLog(String tenDangNhap, String hanhDong, String chiTiet, String diaChiIp) {
        this.thoiGian = LocalDateTime.now();
        this.tenDangNhap = tenDangNhap;
        this.hanhDong = hanhDong;
        this.chiTiet = chiTiet;
        this.diaChiIp = diaChiIp;
    }

    public LocalDateTime getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(LocalDateTime thoiGian) {
        this.thoiGian = thoiGian;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getHanhDong() {
        return hanhDong;
    }

    public void setHanhDong(String hanhDong) {
        this.hanhDong = hanhDong;
    }

    public String getChiTiet() {
        return chiTiet;
    }

    public void setChiTiet(String chiTiet) {
        this.chiTiet = chiTiet;
    }

    public String getDiaChiIp() {
        return diaChiIp;
    }

    public void setDiaChiIp(String diaChiIp) {
        this.diaChiIp = diaChiIp;
    }
}
