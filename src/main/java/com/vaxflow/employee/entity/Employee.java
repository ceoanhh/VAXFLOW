package com.vaxflow.employee.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.vaxflow.core.entity.BaseEntity;

@Entity
@Table(name = "employees")
public class Employee extends BaseEntity {

    @Column(name = "ma_nhan_su", nullable = false, unique = true, length = 50)
    private String maNhanSu;

    @Column(name = "ho_va_ten", nullable = false, length = 150)
    private String hoVaTen;

    @Column(name = "cccd", nullable = false, unique = true, length = 20)
    private String cccd;

    @Column(name = "ngay_sinh", nullable = false)
    private LocalDate ngaySinh;

    @Enumerated(EnumType.STRING)
    @Column(name = "gioi_tinh", nullable = false)
    private Gender gioiTinh;

    @Column(name = "dia_chi", length = 255)
    private String diaChi;

    @Column(name = "sdt", nullable = false, length = 20)
    private String sdt;

    @Column(name = "email", length = 150)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "chuc_vu", nullable = false)
    private Position chucVu;

    @Column(name = "ngay_vao_lam", nullable = false)
    private LocalDate ngayVaoLam;

    @Column(name = "luong_cung", nullable = false, precision = 15, scale = 0)
    private BigDecimal luongCung;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_phong_ban", nullable = false)
    private Department phongBan;

    public String getMaNhanSu() {
        return maNhanSu;
    }

    public void setMaNhanSu(String maNhanSu) {
        this.maNhanSu = maNhanSu;
    }

    public String getHoVaTen() {
        return hoVaTen;
    }

    public void setHoVaTen(String hoVaTen) {
        this.hoVaTen = hoVaTen;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public LocalDate getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(LocalDate ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public Gender getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(Gender gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Position getChucVu() {
        return chucVu;
    }

    public void setChucVu(Position chucVu) {
        this.chucVu = chucVu;
    }

    public LocalDate getNgayVaoLam() {
        return ngayVaoLam;
    }

    public void setNgayVaoLam(LocalDate ngayVaoLam) {
        this.ngayVaoLam = ngayVaoLam;
    }

    public BigDecimal getLuongCung() {
        return luongCung;
    }

    public void setLuongCung(BigDecimal luongCung) {
        this.luongCung = luongCung;
    }

    public Department getPhongBan() {
        return phongBan;
    }

    public void setPhongBan(Department phongBan) {
        this.phongBan = phongBan;
    }
}
