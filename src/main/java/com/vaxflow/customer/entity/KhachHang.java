package com.vaxflow.customer.entity;

import com.vaxflow.core.entity.BaseEntity;
import com.vaxflow.customer.customannotation.UniqueCustomerCode;
import com.vaxflow.customer.customannotation.UniquePhone;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "khach_hang")
@UniquePhone(message = "Số điện thoại này đã tồn tại trong hệ thống!")
@UniqueCustomerCode(message = "Mã khách hàng này đã tồn tại!")
public class KhachHang extends BaseEntity {

    @NotBlank(message = "Mã khách hàng không được để trống")
    @Size(max = 20, message = "Mã khách hàng không vượt quá 20 ký tự")
    @Column(name = "ma_khach_hang", nullable = false, unique = true)
    private String maKhachHang;

    @NotBlank(message = "Họ và tên không được để trống")
    @Size(min = 2, max = 100, message = "Họ và tên phải từ 2 đến 100 ký tự")
    @Column(name = "ten_khach_hang", nullable = false)
    private String tenKhachHang;

    @NotNull(message = "Ngày sinh không được để trống")
    @PastOrPresent(message = "Ngày sinh không thể lớn hơn ngày hiện tại")
    @Column(name = "ngay_sinh", nullable = false)
    private LocalDate ngaySinh;

    @NotBlank(message = "Số điện thoại không được để trống")
    // Ràng buộc CHỈ chứa chữ số (\\d+) và có độ dài từ 9 đến 11 ký tự (chuẩn SĐT Việt Nam: 03x, 05x, 07x, 08x, 09x)
    @Pattern(regexp = "^0\\d{8,10}$", message = "Số điện thoại chỉ được chứa các chữ số, bắt đầu bằng số 0 và dài từ 9-11 số")
    @Column(name = "sdt", nullable = false, unique = true)
    private String sdt;

    @NotBlank(message = "Địa chỉ thường trú không được để trống")
    @Column(name = "dia_chi", nullable = false)
    private String diaChi;

    @OneToMany(mappedBy = "khachHang", cascade = CascadeType.ALL)
    private Set<LichSuTiem> lichSuTiemSet = new HashSet<>();

    // --- CONSTRUCTOR ---
    public KhachHang() {
    }

    // --- GETTER & SETTER ---
    public void addLichSuTiem(LichSuTiem lichSuTiem) {
        lichSuTiemSet.add(lichSuTiem);
        lichSuTiem.setKhachHang(this);
    }

    public void removeLichSuTiem(LichSuTiem lichSuTiem) {
        lichSuTiemSet.remove(lichSuTiem);
        lichSuTiem.setKhachHang(null);
    }

    public Set<LichSuTiem> getLichSuTiemSet() {
        return lichSuTiemSet;
    }

    public void setLichSuTiemSet(Set<LichSuTiem> lichSuTiemSet) {
        this.lichSuTiemSet = lichSuTiemSet;
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
