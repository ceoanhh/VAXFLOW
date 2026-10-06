package com.vaxflow.core.dto;

import java.time.LocalDateTime;

public class HoSoResponseDTO {

    private Long id;
    private String maHoSo;
    private Long idBacSiTiem;
    private Long idBacSiKham;
    private Long idDieuDuong;
    private Long idTiepDon;
    private String ghiChuTheoDoi;
    private LocalDateTime thoiGianTao;

    // Chỉ trả về thông tin cơ bản của LichSuTiem, tránh lộ entity
    private Long idLichSuTiem;

    // Getters & Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public LocalDateTime getThoiGianTao() {
        return thoiGianTao;
    }

    public void setThoiGianTao(LocalDateTime thoiGianTao) {
        this.thoiGianTao = thoiGianTao;
    }

    public Long getIdLichSuTiem() {
        return idLichSuTiem;
    }

    public void setIdLichSuTiem(Long idLichSuTiem) {
        this.idLichSuTiem = idLichSuTiem;
    }

}