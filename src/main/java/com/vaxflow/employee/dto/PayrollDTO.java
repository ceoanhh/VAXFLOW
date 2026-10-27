package com.vaxflow.employee.dto;

import java.math.BigDecimal;

public class PayrollDTO {

    private Long id;
    private Long idNhanSu;
    private String maBangLuong;
    private Integer thang;
    private Integer nam;
    private BigDecimal luongCung;
    private BigDecimal thuong;
    private String lyDoThuong;
    private BigDecimal khauTru;
    private String lyDoKhauTru;
    private BigDecimal thucNhan;

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

    public String getMaBangLuong() {
        return maBangLuong;
    }

    public void setMaBangLuong(String maBangLuong) {
        this.maBangLuong = maBangLuong;
    }

    public Integer getThang() {
        return thang;
    }

    public void setThang(Integer thang) {
        this.thang = thang;
    }

    public Integer getNam() {
        return nam;
    }

    public void setNam(Integer nam) {
        this.nam = nam;
    }

    public BigDecimal getLuongCung() {
        return luongCung;
    }

    public void setLuongCung(BigDecimal luongCung) {
        this.luongCung = luongCung;
    }

    public BigDecimal getThuong() {
        return thuong;
    }

    public void setThuong(BigDecimal thuong) {
        this.thuong = (thuong == null) ? BigDecimal.ZERO : thuong;
    }

    public String getLyDoThuong() {
        return lyDoThuong;
    }

    public void setLyDoThuong(String lyDoThuong) {
        this.lyDoThuong = lyDoThuong;
    }

    public BigDecimal getKhauTru() {
        return khauTru;
    }

    public void setKhauTru(BigDecimal khauTru) {
        this.khauTru = (khauTru == null) ? BigDecimal.ZERO : khauTru;
    }

    public String getLyDoKhauTru() {
        return lyDoKhauTru;
    }

    public void setLyDoKhauTru(String lyDoKhauTru) {
        this.lyDoKhauTru = lyDoKhauTru;
    }

    public BigDecimal getThucNhan() {
        return thucNhan;
    }

    public void setThucNhan(BigDecimal thucNhan) {
        this.thucNhan = thucNhan;
    }
}