package com.vaxflow.employee.dto;

import com.vaxflow.employee.entity.AttendanceStatus;
import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceDTO {

    private Long id;
    private Long idNhanSu;
    private LocalDate ngay;
    private LocalTime gioVao;
    private LocalTime gioRa;
    private AttendanceStatus trangThai;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdNhanSu() {
        return idNhanSu;
    }

    public void setIdNhanSu(Long idNhanSu) {
        this.idNhanSu = idNhanSu;
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
