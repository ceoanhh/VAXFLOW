package com.vaxflow.core.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "lich_su_tiem_chung")
public class VaccinationRecord extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "khach_hang_id", nullable = false)
    private Customer khachHang;

    @Column(name = "ten_vac_xin", nullable = false, length = 150)
    private String tenVacXin;

    @Column(name = "mui_so", nullable = false)
    private Integer muiSo = 1;

    @Column(name = "ngay_tiem", nullable = false)
    private LocalDate ngayTiem;

    @Column(name = "so_lo", length = 50)
    private String soLo;

    @Column(name = "co_so_tiem", length = 150)
    private String coSoTiem;

    @Column(name = "bac_si_kham", length = 100)
    private String bacSiKham;

    @Column(name = "nguoi_tiem", length = 100)
    private String nguoiTiem;

    @Column(name = "trang_thai", nullable = false, length = 30)
    private String trangThai = "DA_TIEM"; // "DA_TIEM", "HEN_TIEM", "DA_HUY"

    @Column(name = "phan_ung_sau_tiem", columnDefinition = "TEXT")
    private String phanUngSauTiem;

    @Column(name = "ngay_hen_mui_tiep")
    private LocalDate ngayHenMuiTiep;

    @Column(name = "ghi_chu", columnDefinition = "TEXT")
    private String ghiChu;

    public VaccinationRecord() {
    }

    public Customer getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(Customer khachHang) {
        this.khachHang = khachHang;
    }

    public String getTenVacXin() {
        return tenVacXin;
    }

    public void setTenVacXin(String tenVacXin) {
        this.tenVacXin = tenVacXin;
    }

    public Integer getMuiSo() {
        return muiSo;
    }

    public void setMuiSo(Integer muiSo) {
        this.muiSo = muiSo;
    }

    public LocalDate getNgayTiem() {
        return ngayTiem;
    }

    public void setNgayTiem(LocalDate ngayTiem) {
        this.ngayTiem = ngayTiem;
    }

    public String getSoLo() {
        return soLo;
    }

    public void setSoLo(String soLo) {
        this.soLo = soLo;
    }

    public String getCoSoTiem() {
        return coSoTiem;
    }

    public void setCoSoTiem(String coSoTiem) {
        this.coSoTiem = coSoTiem;
    }

    public String getBacSiKham() {
        return bacSiKham;
    }

    public void setBacSiKham(String bacSiKham) {
        this.bacSiKham = bacSiKham;
    }

    public String getNguoiTiem() {
        return nguoiTiem;
    }

    public void setNguoiTiem(String nguoiTiem) {
        this.nguoiTiem = nguoiTiem;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getPhanUngSauTiem() {
        return phanUngSauTiem;
    }

    public void setPhanUngSauTiem(String phanUngSauTiem) {
        this.phanUngSauTiem = phanUngSauTiem;
    }

    public LocalDate getNgayHenMuiTiep() {
        return ngayHenMuiTiep;
    }

    public void setNgayHenMuiTiep(LocalDate ngayHenMuiTiep) {
        this.ngayHenMuiTiep = ngayHenMuiTiep;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}
