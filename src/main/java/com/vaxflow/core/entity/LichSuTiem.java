package com.vaxflow.core.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "lich_su_tiem")
public class LichSuTiem extends BaseEntity {

    @Column(name = "ma_tiem", nullable = false, unique = true)
    private String maTiem;

    @OneToMany(mappedBy = "lichSuTiem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HoSo> hoSoList = new ArrayList<>();

    // Quan hệ N - N duy nhất với KhachHang
    @ManyToMany(mappedBy = "lichSuTiemSet")
    private Set<KhachHang> khachHangSet = new HashSet<>();

    @Column(name = "id_vaccine", nullable = false)
    private Long idVaccine;

    @Column(name = "id_lo_vaccine", nullable = false)
    private Long idLoVaccine;

    @Column(name = "so_mui_tiem", nullable = false)
    private Integer soMuiTiem;

    @Column(name = "ngay_tiem", nullable = false)
    private LocalDate ngayTiem;

    @Column(name = "trang_thai")
    private String trangThai;

    // --- CONSTRUCTOR ---
    public LichSuTiem() {
    }

    // --- HELPER METHODS FOR HOSO ---
    public void addHoSo(HoSo hoSo) {
        hoSoList.add(hoSo);
        hoSo.setLichSuTiem(this);
    }

    public void removeHoSo(HoSo hoSo) {
        hoSoList.remove(hoSo);
        hoSo.setLichSuTiem(null);
    }

    // --- GETTER & SETTER ---
    public String getMaTiem() {
        return maTiem;
    }

    public void setMaTiem(String maTiem) {
        this.maTiem = maTiem;
    }

    public List<HoSo> getHoSoList() {
        return hoSoList;
    }

    public void setHoSoList(List<HoSo> hoSoList) {
        this.hoSoList = hoSoList;
    }

    public Set<KhachHang> getKhachHangSet() {
        return khachHangSet;
    }

    public void setKhachHangSet(Set<KhachHang> khachHangSet) {
        this.khachHangSet = khachHangSet;
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