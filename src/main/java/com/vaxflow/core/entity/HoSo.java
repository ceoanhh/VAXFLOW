package com.vaxflow.core.entity;

import jakarta.persistence.*;

@Entity
@Table(
        name = "ho_so",
        indexes = {
                @Index(name = "idx_ho_so_ma", columnList = "ma_ho_so"),
                @Index(name = "idx_ho_so_bac_si_kham", columnList = "id_bac_si_kham"),
                @Index(name = "idx_ho_so_thoi_gian_tao", columnList = "thoi_gian_tao")
        }
)
public class HoSo extends BaseEntity {

    @Column(name = "ma_ho_so", nullable = false, unique = true)
    private String maHoSo;

    @Column(name = "id_bac_si_tiem", nullable = false)
    private Long idBacSiTiem;

    @Column(name = "id_bac_si_kham", nullable = false)
    private Long idBacSiKham;

    @Column(name = "id_dieu_duong", nullable = false) // Đã sửa tên cột
    private Long idDieuDuong;

    @Column(name = "id_tiep_don", nullable = false)
    private Long idTiepDon;

    @Column(name = "ghi_chu_theo_doi")
    private String ghiChuTheoDoi;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "id_tiem",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_ho_so_lich_su_tiem")
    )
    private LichSuTiem lichSuTiem;

    // Getters & Setters ...

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

    public LichSuTiem getLichSuTiem() {
        return lichSuTiem;
    }

    public void setLichSuTiem(LichSuTiem lichSuTiem) {
        this.lichSuTiem = lichSuTiem;
    }
}