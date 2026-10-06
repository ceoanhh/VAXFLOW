package com.vaxflow.core.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class HoSoRequestDTO {

    @NotBlank(message = "Mã hồ sơ không được để trống")
    private String maHoSo;

    @NotNull(message = "ID bác sĩ tiêm không được để trống")
    private Long idBacSiTiem;

    @NotNull(message = "ID bác sĩ khám không được để trống")
    private Long idBacSiKham;

    @NotNull(message = "ID điều dưỡng không được để trống")
    private Long idDieuDuong;

    @NotNull(message = "ID tiếp đón không được để trống")
    private Long idTiepDon;

    private String ghiChuTheoDoi;

    @NotNull(message = "ID lịch sử tiêm không được để trống")
    private Long idLichSuTiem;

    // 1. No-args constructor (bắt buộc giữ lại cho Jackson parse JSON)
    public HoSoRequestDTO() {
    }

    // 2. All-args constructor (phục vụ viết Unit Test / khởi tạo nhanh)
    public HoSoRequestDTO(String maHoSo, Long idBacSiTiem, Long idBacSiKham,
                          Long idDieuDuong, Long idTiepDon,
                          String ghiChuTheoDoi, Long idLichSuTiem) {
        this.maHoSo = maHoSo;
        this.idBacSiTiem = idBacSiTiem;
        this.idBacSiKham = idBacSiKham;
        this.idDieuDuong = idDieuDuong;
        this.idTiepDon = idTiepDon;
        this.ghiChuTheoDoi = ghiChuTheoDoi;
        this.idLichSuTiem = idLichSuTiem;
    }

    // Getters & Setters
    public String getMaHoSo() {
        return maHoSo;
    }

    public void setMaHoSo(String maHoSo) {
        this.maHoSo = maHoSo;
    }

    public Long getIdBacSiTiem() {
        return idBacSiTiem;
    }

    public void setIdBacSiTiem(Long idBacSiTiem) {
        this.idBacSiTiem = idBacSiTiem;
    }

    public Long getIdBacSiKham() {
        return idBacSiKham;
    }

    public void setIdBacSiKham(Long idBacSiKham) {
        this.idBacSiKham = idBacSiKham;
    }

    public Long getIdDieuDuong() {
        return idDieuDuong;
    }

    public void setIdDieuDuong(Long idDieuDuong) {
        this.idDieuDuong = idDieuDuong;
    }

    public Long getIdTiepDon() {
        return idTiepDon;
    }

    public void setIdTiepDon(Long idTiepDon) {
        this.idTiepDon = idTiepDon;
    }

    public String getGhiChuTheoDoi() {
        return ghiChuTheoDoi;
    }

    public void setGhiChuTheoDoi(String ghiChuTheoDoi) {
        this.ghiChuTheoDoi = ghiChuTheoDoi;
    }

    public Long getIdLichSuTiem() {
        return idLichSuTiem;
    }

    public void setIdLichSuTiem(Long idLichSuTiem) {
        this.idLichSuTiem = idLichSuTiem;
    }

}
