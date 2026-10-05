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
@Table(name = "hoa_don_khach_hang")
public class HoaDonKhachHang extends BaseEntity {

    @Column(name = "ma_hoa_don", nullable = false, unique = true, length = 40)
    private String maHoaDon;

    // Theo tài liệu nhóm, đây là mã hồ sơ/liệu trình gắn với hóa đơn tổng.
    @Column(name = "id_ho_so")
    private Long idHoSo;

    @Column(name = "tong_tien", nullable = false, precision = 15, scale = 0)
    private BigDecimal tongTien;

    @Column(name = "ngay_lap", nullable = false)
    private LocalDateTime ngayLap;

    @Enumerated(EnumType.STRING)
    @Column(name = "trang_thai", nullable = false, length = 30)
    private TrangThaiHoaDon trangThai;

    @Column(name = "ghi_chu", length = 1000)
    private String ghiChu;

    public String getMaHoaDon() { return maHoaDon; }
    public void setMaHoaDon(String maHoaDon) { this.maHoaDon = maHoaDon; }
    public Long getIdHoSo() { return idHoSo; }
    public void setIdHoSo(Long idHoSo) { this.idHoSo = idHoSo; }
    public BigDecimal getTongTien() { return tongTien; }
    public void setTongTien(BigDecimal tongTien) { this.tongTien = tongTien; }
    public LocalDateTime getNgayLap() { return ngayLap; }
    public void setNgayLap(LocalDateTime ngayLap) { this.ngayLap = ngayLap; }
    public TrangThaiHoaDon getTrangThai() { return trangThai; }
    public void setTrangThai(TrangThaiHoaDon trangThai) { this.trangThai = trangThai; }
    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }
}
