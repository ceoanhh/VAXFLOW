package com.vaxflow.employee.dto;

import java.math.BigDecimal;

public class PayrollDTO {

    private Long id;
    private Long idNhanSu;
    private Integer thang;
    private Integer nam;
    private BigDecimal luongCung;
    private BigDecimal thuong;
    private BigDecimal khauTru;
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
        this.thuong = thuong;
    }

    public BigDecimal getKhauTru() {
        return khauTru;
    }

    public void setKhauTru(BigDecimal khauTru) {
        this.khauTru = khauTru;
    }

    public BigDecimal getThucNhan() {
        return thucNhan;
    }

    public void setThucNhan(BigDecimal thucNhan) {
        this.thucNhan = thucNhan;
    }
}
