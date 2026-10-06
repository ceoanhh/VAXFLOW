package com.vaxflow.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "payrolls")
public class Payroll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_nhan_su", nullable = false)
    private Employee nhanSu;

    @Column(name = "thang", nullable = false)
    private Integer thang;

    @Column(name = "nam", nullable = false)
    private Integer nam;

    @Column(name = "luong_cung", nullable = false, precision = 15, scale = 0)
    private BigDecimal luongCung;

    @Column(name = "thuong", nullable = false, precision = 15, scale = 0)
    private BigDecimal thuong;

    @Column(name = "khau_tru", nullable = false, precision = 15, scale = 0)
    private BigDecimal khauTru;

    @Column(name = "thuc_nhan", nullable = false, precision = 15, scale = 0)
    private BigDecimal thucNhan;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getNhanSu() {
        return nhanSu;
    }

    public void setNhanSu(Employee nhanSu) {
        this.nhanSu = nhanSu;
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
