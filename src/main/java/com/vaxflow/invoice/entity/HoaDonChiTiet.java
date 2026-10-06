package com.vaxflow.invoice.entity;

import com.vaxflow.core.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.math.BigDecimal;

@Entity
@Table(name = "hoa_don_chi_tiet", uniqueConstraints =
        @UniqueConstraint(name = "uk_hoa_don_chi_tiet_id_tiem", columnNames = "id_tiem"))
public class HoaDonChiTiet extends BaseEntity {

    @Column(name = "id_hoa_don", nullable = false)
    private Long idHoaDon;

    // Giữ nguyên tên cột id_tiem theo tài liệu; adapter Person 2 xác nhận ID record tương ứng.
    @Column(name = "id_tiem", nullable = false, unique = true)
    private Long idTiem;

    @Column(name = "so_luong", nullable = false)
    private Integer soLuong;

    @Column(name = "don_gia", nullable = false, precision = 15, scale = 2)
    private BigDecimal donGia;

    @Column(name = "thanh_tien", nullable = false, precision = 15, scale = 2)
    private BigDecimal thanhTien;

    @Column(name = "ghi_chu", length = 1000)
    private String ghiChu;

    public Long getIdHoaDon() { return idHoaDon; }
    public void setIdHoaDon(Long idHoaDon) { this.idHoaDon = idHoaDon; }
    public Long getIdTiem() { return idTiem; }
    public void setIdTiem(Long idTiem) { this.idTiem = idTiem; }
    public Integer getSoLuong() { return soLuong; }
    public void setSoLuong(Integer soLuong) { this.soLuong = soLuong; }
    public BigDecimal getDonGia() { return donGia; }
    public void setDonGia(BigDecimal donGia) { this.donGia = donGia; }
    public BigDecimal getThanhTien() { return thanhTien; }
    public void setThanhTien(BigDecimal thanhTien) { this.thanhTien = thanhTien; }
    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }
}
