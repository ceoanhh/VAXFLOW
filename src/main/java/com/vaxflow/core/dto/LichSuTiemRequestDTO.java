package com.vaxflow.core.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class LichSuTiemRequestDTO {
    @NotBlank(message = "Mã tiêm không được để trống")
    private String maTiem;

    @NotNull(message = "ID Vắc-xin không được để trống")
    private Long idVaccine;

    @NotNull(message = "ID Lô vắc-xin không được để trống")
    private Long idLoVaccine;

    @NotNull(message = "Số mũi tiêm không được để trống")
    @Min(value = 1, message = "Số mũi tiêm phải lớn hơn 0")
    private Integer soMuiTiem;

    @NotNull(message = "Ngày tiêm không được để trống")
    private LocalDate ngayTiem;

    private String trangThai;

    // Getters and Setters...

    public String getMaTiem() {
        return maTiem;
    }

    public void setMaTiem(String maTiem) {
        this.maTiem = maTiem;
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