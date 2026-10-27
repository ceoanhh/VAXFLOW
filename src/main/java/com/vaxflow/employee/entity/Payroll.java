package com.vaxflow.employee.entity;

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

    @Column(name = "ma_bang_luong", nullable = false)
    private String maBangLuong;

    @Column(name = "thang", nullable = false)
    private Integer thang;

    @Column(name = "nam", nullable = false)
    private Integer nam;

    @Column(name = "luong_cung", nullable = false, precision = 15, scale = 0)
    private BigDecimal luongCung;

    @Column(name = "thuong", nullable = true, precision = 15, scale = 0)
    private BigDecimal thuong;

    @Column(name = "ly_do_thuong", nullable = true)
    private String lyDoThuong;

    @Column(name = "khau_tru", nullable = true, precision = 15, scale = 0)
    private BigDecimal khauTru;

    @Column(name = "ly_do_khau_tru", nullable = true)
    private String lyDoKhauTru;

    @Column(name = "thuc_nhan", nullable = false, precision = 15, scale = 0)
    private BigDecimal thucNhan;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMaBangLuong() {
        return maBangLuong;
    }

    public void setMaBangLuong(String maBangLuong) {
        this.maBangLuong = maBangLuong;
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
        this.khauTru = khauTru;
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
