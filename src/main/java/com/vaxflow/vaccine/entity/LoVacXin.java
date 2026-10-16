package com.vaxflow.vaccine.entity;

import com.vaxflow.core.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "lo_vac_xin", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id_vac_xin", "ma_lo"}) // Index unique theo thiết kế
})
public class LoVacXin extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "id_vac_xin", nullable = false)
    private VacXin vacXin;

    @Column(name = "ma_lo", length = 100, nullable = false)
    private String maLo;

    @Column(name = "ngay_nhap", nullable = false)
    private LocalDateTime ngayNhap;

    @Column(name = "han_su_dung", nullable = false)
    private LocalDate hanSuDung;

    @Column(name = "so_luong_nhap", nullable = false)
    private int soLuongNhap;

    @Column(name = "so_luong_con_lai", nullable = false)
    private int soLuongConLai;

    public LoVacXin() {
    }

    public VacXin getVacXin() {
        return vacXin;
    }

    public void setVacXin(VacXin vacXin) {
        this.vacXin = vacXin;
    }

    public String getMaLo() {
        return maLo;
    }

    public void setMaLo(String maLo) {
        this.maLo = maLo;
    }

    public LocalDateTime getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(LocalDateTime ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public LocalDate getHanSuDung() {
        return hanSuDung;
    }

    public void setHanSuDung(LocalDate hanSuDung) {
        this.hanSuDung = hanSuDung;
    }

    public int getSoLuongNhap() {
        return soLuongNhap;
    }

    public void setSoLuongNhap(int soLuongNhap) {
        this.soLuongNhap = soLuongNhap;
    }

    public int getSoLuongConLai() {
        return soLuongConLai;
    }

    public void setSoLuongConLai(int soLuongConLai) {
        this.soLuongConLai = soLuongConLai;
    }
}
