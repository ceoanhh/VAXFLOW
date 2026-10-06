package com.vaxflow.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "attendances")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_nhan_su", nullable = false)
    private Employee nhanSu;

    @Column(name = "ngay", nullable = false)
    private LocalDate ngay;

    @Column(name = "gio_vao")
    private LocalTime gioVao;

    @Column(name = "gio_ra")
    private LocalTime gioRa;

    @Enumerated(EnumType.STRING)
    @Column(name = "trang_thai", nullable = false)
    private AttendanceStatus trangThai;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getNhanSu() {
        return nhanSu;
    }

    public void setNhanSu(Employee nhanSu) {
        this.nhanSu = nhanSu;
    }

    public LocalDate getNgay() {
        return ngay;
    }

    public void setNgay(LocalDate ngay) {
        this.ngay = ngay;
    }

    public LocalTime getGioVao() {
        return gioVao;
    }

    public void setGioVao(LocalTime gioVao) {
        this.gioVao = gioVao;
    }

    public LocalTime getGioRa() {
        return gioRa;
    }

    public void setGioRa(LocalTime gioRa) {
        this.gioRa = gioRa;
    }

    public AttendanceStatus getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(AttendanceStatus trangThai) {
        this.trangThai = trangThai;
    }
}
