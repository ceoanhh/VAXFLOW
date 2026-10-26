package com.vaxflow.customer.entity;

import com.vaxflow.core.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;


@Entity
@Table(name = "lich_su_tiem")
public class LichSuTiem extends BaseEntity {

    @NotBlank(message = "Mã tiêm không được để trống")
    @Size(max = 50, message = "Mã tiêm không vượt quá 50 ký tự")
    @Column(name = "ma_tiem", nullable = false, unique = true)
    private String maTiem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ho_so")
    private HoSo hoSo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_khach_hang")
    private KhachHang khachHang;

    @NotNull(message = "Vui lòng nhập ID Vaccine")
    @Column(name = "id_vaccine", nullable = false)
    private Long idVaccine;

    @NotNull(message = "Vui lòng nhập ID Lô Vaccine")
    @Column(name = "id_lo_vaccine", nullable = false)
    private Long idLoVaccine;

    @NotNull(message = "Vui lòng nhập số mũi tiêm")
    @Min(value = 1, message = "Mũi tiêm phải từ mũi 1 trở lên")
    @Column(name = "so_mui_tiem", nullable = false)
    private Integer soMuiTiem;

    @NotNull(message = "Vui lòng chọn ngày tiêm")
    @PastOrPresent(message = "Ngày tiêm không được vượt quá ngày hiện tại")
    @Column(name = "ngay_tiem", nullable = false)
    private LocalDate ngayTiem;

    @Column(name = "trang_thai")
    private String trangThai;

    public LichSuTiem() {
    }

    // --- GETTER & SETTER ---
    public String getMaTiem() {
        return maTiem;
    }

    public void setMaTiem(String maTiem) {
        this.maTiem = maTiem;
    }

    public HoSo getHoSo() {
        return hoSo;
    }

    public void setHoSo(HoSo hoSo) {
        this.hoSo = hoSo;
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }

    public Long getIdVaccine() {
        return idVaccine;
    }

    public void setIdVaccine(Long idVaccine) {
        this.idVaccine = idVaccine;
    }

    public Long getIdLoVaccine() {
        return idLoVaccine;
    }

    public void setIdLoVaccine(Long idLoVaccine) {
        this.idLoVaccine = idLoVaccine;
    }

    public Integer getSoMuiTiem() {
        return soMuiTiem;
    }

    public void setSoMuiTiem(Integer soMuiTiem) {
        this.soMuiTiem = soMuiTiem;
    }

    public LocalDate getNgayTiem() {
        return ngayTiem;
    }

    public void setNgayTiem(LocalDate ngayTiem) {
        this.ngayTiem = ngayTiem;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}