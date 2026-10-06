package com.vaxflow.invoice.entity;

import com.vaxflow.core.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "thanh_toan")
public class ThanhToan extends BaseEntity {

    @Column(name = "ma_thanh_toan", nullable = false, unique = true, length = 40)
    private String maThanhToan;

    @Column(name = "id_hoa_don", nullable = false, unique = true)
    private Long idHoaDon;

    @Column(name = "id_thu_ngan")
    private Long idThuNgan;

    @Column(name = "so_tien", nullable = false, precision = 15, scale = 2)
    private BigDecimal soTien;

    @Column(name = "ngay_thu", nullable = false)
    private LocalDateTime ngayThu;

    @Enumerated(EnumType.STRING)
    @Column(name = "phuong_thuc_tt", nullable = false, length = 30)
    private PhuongThucThanhToan phuongThucTt;

    @Column(name = "ghi_chu", length = 1000)
    private String ghiChu;

    public String getMaThanhToan() { return maThanhToan; }
    public void setMaThanhToan(String maThanhToan) { this.maThanhToan = maThanhToan; }
    public Long getIdHoaDon() { return idHoaDon; }
    public void setIdHoaDon(Long idHoaDon) { this.idHoaDon = idHoaDon; }
    public Long getIdThuNgan() { return idThuNgan; }
    public void setIdThuNgan(Long idThuNgan) { this.idThuNgan = idThuNgan; }
    public BigDecimal getSoTien() { return soTien; }
    public void setSoTien(BigDecimal soTien) { this.soTien = soTien; }
    public LocalDateTime getNgayThu() { return ngayThu; }
    public void setNgayThu(LocalDateTime ngayThu) { this.ngayThu = ngayThu; }
    public PhuongThucThanhToan getPhuongThucTt() { return phuongThucTt; }
    public void setPhuongThucTt(PhuongThucThanhToan phuongThucTt) { this.phuongThucTt = phuongThucTt; }
    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }
}
