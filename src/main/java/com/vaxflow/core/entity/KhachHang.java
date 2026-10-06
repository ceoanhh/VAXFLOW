package com.vaxflow.core.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "khach_hang")
public class KhachHang extends BaseEntity {

    @Column(name = "ma_khach_hang", nullable = false, unique = true)
    private String maKhachHang;

    @Column(name = "ten_khach_hang", nullable = false)
    private String tenKhachHang;

    @Column(name = "sdt", nullable = false)
    private String sdt;

    @Column(name = "ngay_sinh", nullable = false)
    private LocalDate ngaySinh;

    @Column(name = "dia_chi", nullable = false)
    private String diaChi;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "khach_hang_lich_su_tiem",
            joinColumns = @JoinColumn(name = "id_khach_hang"),
            inverseJoinColumns = @JoinColumn(name = "id_tiem")
    )
    private Set<LichSuTiem> lichSuTiemSet = new HashSet<>();

    // --- CONSTRUCTOR ---
    public KhachHang() {
    }

    // --- GETTER & SETTER ---
    public void addLichSuTiem(LichSuTiem lst) {
        this.lichSuTiemSet.add(lst);
        lst.getKhachHangSet().add(this);
    }

    public void removeLichSuTiem(LichSuTiem lst) {
        this.lichSuTiemSet.remove(lst);
        lst.getKhachHangSet().remove(this);
    }

    public Set<LichSuTiem> getLichSuTiemSet() {
        return lichSuTiemSet;
    }

    public void setLichSuTiemSet(Set<LichSuTiem> lichSuTiemSet) {
        this.lichSuTiemSet = lichSuTiemSet;
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public LocalDate getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(LocalDate ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
}
