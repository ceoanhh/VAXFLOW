package com.vaxflow.core.dto;

import com.vaxflow.core.entity.KhachHang;

import java.time.LocalDate;

// KhachHangResponse.java
public class KhachHangResponseDTO {
    private Long id;
    private String maKhachHang;
    private String tenKhachHang;
    private String sdt;
    private LocalDate ngaySinh;
    private String diaChi;

    // Static mapper method đơn giản (hoặc dùng MapStruct)
    public static KhachHangResponseDTO fromEntity(KhachHang entity) {
        KhachHangResponseDTO response = new KhachHangResponseDTO();
        response.setId(entity.getId());
        response.setMaKhachHang(entity.getMaKhachHang());
        response.setTenKhachHang(entity.getTenKhachHang());
        response.setSdt(entity.getSdt());
        response.setNgaySinh(entity.getNgaySinh());
        response.setDiaChi(entity.getDiaChi());
        return response;
    }

    // Getters & Setters...
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
