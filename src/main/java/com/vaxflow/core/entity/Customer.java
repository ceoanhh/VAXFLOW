package com.vaxflow.core.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "khach_hang")
public class Customer extends BaseEntity {

    @Column(name = "ho_ten", nullable = false, length = 100)
    private String hoTen;

    @Column(name = "ngay_sinh")
    private LocalDate ngaySinh;

    @Column(name = "gioi_tinh", length = 10)
    private String gioiTinh;

    @Column(name = "so_dien_thoai", nullable = false, length = 20)
    private String soDienThoai;

    @Column(name = "cccd", length = 30)
    private String cccd;

    @Column(name = "dia_chi", length = 255)
    private String diaChi;

    @Column(name = "nguoi_giam_ho", length = 100)
    private String nguoiGiamHo;

    @Column(name = "so_dien_thoai_giam_ho", length = 20)
    private String soDienThoaiGiamHo;

    @Column(name = "tien_su_benh", columnDefinition = "TEXT")
    private String tienSuBenh;

    @Column(name = "ghi_chu", columnDefinition = "TEXT")
    private String ghiChu;

    @OneToMany(mappedBy = "khachHang", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("ngayTiem DESC")
    private List<VaccinationRecord> danhSachMuiTiem = new ArrayList<>();

    public Customer() {
    }

    public Customer(String hoTen, LocalDate ngaySinh, String gioiTinh, String soDienThoai, String cccd, String diaChi) {
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.soDienThoai = soDienThoai;
        this.cccd = cccd;
        this.diaChi = diaChi;
    }

    // Getters and Setters
    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public LocalDate getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(LocalDate ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getNguoiGiamHo() {
        return nguoiGiamHo;
    }

    public void setNguoiGiamHo(String nguoiGiamHo) {
        this.nguoiGiamHo = nguoiGiamHo;
    }

    public String getSoDienThoaiGiamHo() {
        return soDienThoaiGiamHo;
    }

    public void setSoDienThoaiGiamHo(String soDienThoaiGiamHo) {
        this.soDienThoaiGiamHo = soDienThoaiGiamHo;
    }

    public String getTienSuBenh() {
        return tienSuBenh;
    }

    public void setTienSuBenh(String tienSuBenh) {
        this.tienSuBenh = tienSuBenh;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public List<VaccinationRecord> getDanhSachMuiTiem() {
        return danhSachMuiTiem;
    }

    public void setDanhSachMuiTiem(List<VaccinationRecord> danhSachMuiTiem) {
        this.danhSachMuiTiem = danhSachMuiTiem;
    }
}
