package com.vaxflow.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "quan_tri_vien")
public class Admin extends BaseEntity {

    @Column(name = "ten_dang_nhap", nullable = false, unique = true, length = 50)
    private String tenDangNhap;

    @Column(name = "mat_khau", nullable = false)
    private String matKhau;

    @Column(name = "ho_ten", nullable = false, length = 100)
    private String hoTen;

    @Enumerated(EnumType.STRING)
    @Column(name = "vai_tro", nullable = false, length = 30)
    private Role vaiTro = Role.ROLE_ADMIN;

    @Column(name = "so_dien_thoai", unique = true, length = 20)
    private String soDienThoai;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "id_nhan_vien")
    private Long idNhanVien;

    @Column(name = "trang_thai", nullable = false)
    private boolean trangThai = true;

    // Bao ve tai khoan: Khoa sau 5 lan nhap sai
    @Column(name = "failed_attempts", nullable = false)
    private int failedAttempts = 0;

    @Column(name = "account_locked", nullable = false)
    private boolean accountLocked = false;

    @Column(name = "lock_time")
    private LocalDateTime lockTime;

    // Ngay doi mat khau gan nhat de theo doi doi mat khau dinh ky
    @Column(name = "ngay_doi_mat_khau")
    private LocalDateTime ngayDoiMatKhau;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    public Admin() {
    }

    public Admin(String tenDangNhap, String matKhau, String hoTen) {
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.hoTen = hoTen;
        this.vaiTro = Role.ROLE_ADMIN;
        this.trangThai = true;
        this.ngayDoiMatKhau = LocalDateTime.now();
    }

    public Admin(String tenDangNhap, String matKhau, String hoTen, Role vaiTro, String soDienThoai) {
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.hoTen = hoTen;
        this.vaiTro = (vaiTro != null) ? vaiTro : Role.ROLE_ADMIN;
        this.soDienThoai = soDienThoai;
        this.trangThai = true;
        this.ngayDoiMatKhau = LocalDateTime.now();
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public Role getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(Role vaiTro) {
        this.vaiTro = vaiTro;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getIdNhanVien() {
        return idNhanVien;
    }

    public void setIdNhanVien(Long idNhanVien) {
        this.idNhanVien = idNhanVien;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public int getFailedAttempts() {
        return failedAttempts;
    }

    public void setFailedAttempts(int failedAttempts) {
        this.failedAttempts = failedAttempts;
    }

    public boolean isAccountLocked() {
        return accountLocked;
    }

    public void setAccountLocked(boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    public LocalDateTime getLockTime() {
        return lockTime;
    }

    public void setLockTime(LocalDateTime lockTime) {
        this.lockTime = lockTime;
    }

    public LocalDateTime getNgayDoiMatKhau() {
        return ngayDoiMatKhau;
    }

    public void setNgayDoiMatKhau(LocalDateTime ngayDoiMatKhau) {
        this.ngayDoiMatKhau = ngayDoiMatKhau;
    }

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    public void setLastLoginAt(LocalDateTime lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }
}