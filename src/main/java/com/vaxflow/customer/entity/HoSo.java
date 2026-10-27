package com.vaxflow.customer.entity;

import com.vaxflow.core.entity.BaseEntity;
import com.vaxflow.employee.entity.Employee;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "ho_so",
        indexes = {
                @Index(name = "idx_ho_so_ma", columnList = "ma_ho_so"),
                @Index(name = "idx_ho_so_bac_si_kham", columnList = "bac_si_kham")
        }
)
public class HoSo extends BaseEntity {

    @NotBlank(message = "Mã hồ sơ không được để trống")
    @Size(max = 50, message = "Mã hồ sơ không vượt quá 50 ký tự")
    @Column(name = "ma_ho_so", nullable = false, unique = true)
    private String maHoSo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_khach_hang", nullable = false)
    private KhachHang khachHang;

    @NotNull(message = "Vui lòng chọn Bác sĩ tiêm")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bac_si_tiem", nullable = false)
    private Employee bacSiTiem;

    @NotNull(message = "Vui lòng chọn Bác sĩ khám")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bac_si_kham", nullable = false)
    private Employee bacSiKham;

    @NotNull(message = "Vui lòng chọn Điều dưỡng")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dieu_duong", nullable = false)
    private Employee dieuDuong;

    @NotNull(message = "Vui lòng chọn Nhân viên tiếp đón")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tiep_don", nullable = false)
    private Employee tiepDon;

    @Column(name = "ghi_chu_theo_doi")
    private String ghiChuTheoDoi;

    @OneToMany(mappedBy = "hoSo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LichSuTiem> lichSuTiemList = new ArrayList<>();

    public HoSo() {
    }

    // --- HELPER METHODS DÙNG CHO LICHSUTIEM ---
    public void addLichSuTiem(LichSuTiem lichSuTiem) {
        lichSuTiemList.add(lichSuTiem);
        lichSuTiem.setHoSo(this);
    }

    public void removeLichSuTiem(LichSuTiem lichSuTiem) {
        lichSuTiemList.remove(lichSuTiem);
        lichSuTiem.setHoSo(null);
    }

    // --- GETTERS & SETTERS ---
    public String getMaHoSo() {
        return maHoSo;
    }

    public void setMaHoSo(String maHoSo) {
        this.maHoSo = maHoSo;
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }

    public Employee getBacSiTiem() {
        return bacSiTiem;
    }

    public void setBacSiTiem(Employee bacSiTiem) {
        this.bacSiTiem = bacSiTiem;
    }

    public Employee getBacSiKham() {
        return bacSiKham;
    }

    public void setBacSiKham(Employee bacSiKham) {
        this.bacSiKham = bacSiKham;
    }

    public Employee getDieuDuong() {
        return dieuDuong;
    }

    public void setDieuDuong(Employee dieuDuong) {
        this.dieuDuong = dieuDuong;
    }

    public Employee getTiepDon() {
        return tiepDon;
    }

    public void setTiepDon(Employee tiepDon) {
        this.tiepDon = tiepDon;
    }

    public String getGhiChuTheoDoi() {
        return ghiChuTheoDoi;
    }

    public void setGhiChuTheoDoi(String ghiChuTheoDoi) {
        this.ghiChuTheoDoi = ghiChuTheoDoi;
    }

    public List<LichSuTiem> getLichSuTiemList() {
        return lichSuTiemList;
    }

    public void setLichSuTiemList(List<LichSuTiem> lichSuTiemList) {
        this.lichSuTiemList = lichSuTiemList;
    }
}