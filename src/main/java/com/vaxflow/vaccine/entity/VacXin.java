package com.vaxflow.vaccine.entity;

import com.vaxflow.core.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "vac_xin")
public class VacXin extends BaseEntity {

    @Column(name = "ma_vac_xin", length = 50, nullable = false, unique = true)
    private String maVacXin;

    @Column(name = "ten_vac_xin", length = 150, nullable = false)
    private String tenVacXin;

    @Column(name = "nha_san_xuat", length = 150, nullable = false)
    private String nhaSanXuat;

    @Column(name = "loai_benh_phong", length = 255, nullable = false)
    private String loaiBenhPhong;

    @Column(name = "so_mui_can_tiem", nullable = false)
    private int soMuiCanTiem;

    @Column(name = "khoang_cach_ngay")
    private Integer khoangCachNgay; // Có thể null nên dùng Integer

    @Column(name = "don_gia", precision = 15, scale = 0, nullable = false)
    private BigDecimal donGia;

    public VacXin() {
    }

    public String getMaVacXin() {
        return maVacXin;
    }

    public void setMaVacXin(String maVacXin) {
        this.maVacXin = maVacXin;
    }

    public String getTenVacXin() {
        return tenVacXin;
    }

    public void setTenVacXin(String tenVacXin) {
        this.tenVacXin = tenVacXin;
    }

    public String getNhaSanXuat() {
        return nhaSanXuat;
    }

    public void setNhaSanXuat(String nhaSanXuat) {
        this.nhaSanXuat = nhaSanXuat;
    }

    public String getLoaiBenhPhong() {
        return loaiBenhPhong;
    }

    public void setLoaiBenhPhong(String loaiBenhPhong) {
        this.loaiBenhPhong = loaiBenhPhong;
    }

    public int getSoMuiCanTiem() {
        return soMuiCanTiem;
    }

    public void setSoMuiCanTiem(int soMuiCanTiem) {
        this.soMuiCanTiem = soMuiCanTiem;
    }

    public Integer getKhoangCachNgay() {
        return khoangCachNgay;
    }

    public void setKhoangCachNgay(Integer khoangCachNgay) {
        this.khoangCachNgay = khoangCachNgay;
    }

    public BigDecimal getDonGia() {
        return donGia;
    }

    public void setDonGia(BigDecimal donGia) {
        this.donGia = donGia;
    }
}
