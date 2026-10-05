package com.vaxflow.core.dto;

import java.math.BigDecimal;

/**
 * DTO dai dien cho thong tin Vac-xin hien thi o phia nguoi dung
 */
public class VaccineDto {

    private String maVacXin;
    private String tenVacXin;
    private String nhaSanXuat;
    private String xuatXu;
    private String phongBenh;
    private String doiTuong;
    private String phacDoTiem;
    private BigDecimal giaNiemYet;
    private String tinhTrang; // "CON_HANG", "SAP_VE", "TAM_HET"
    private String phanLoai;  // "TRE_EM", "TIEN_HON_NHAN", "NGUOI_LON", "TAT_CA"
    private boolean noiBat;

    public VaccineDto() {
    }

    public VaccineDto(String maVacXin, String tenVacXin, String nhaSanXuat, String xuatXu,
                      String phongBenh, String doiTuong, String phacDoTiem,
                      BigDecimal giaNiemYet, String tinhTrang, String phanLoai, boolean noiBat) {
        this.maVacXin = maVacXin;
        this.tenVacXin = tenVacXin;
        this.nhaSanXuat = nhaSanXuat;
        this.xuatXu = xuatXu;
        this.phongBenh = phongBenh;
        this.doiTuong = doiTuong;
        this.phacDoTiem = phacDoTiem;
        this.giaNiemYet = giaNiemYet;
        this.tinhTrang = tinhTrang;
        this.phanLoai = phanLoai;
        this.noiBat = noiBat;
    }

    // Getters and Setters
    public String getMaVacXin() {
        return maVacXin;
    }

    public void setMaVacXin(String maVacXin) {
        this.maVacXin = maVacXin;
    }

    public String getTenVacXin() {
        return tenVacXin;
    }

    public void setTenVacXin(String tenVacXin) {
        this.tenVacXin = tenVacXin;
    }

    public String getNhaSanXuat() {
        return nhaSanXuat;
    }

    public void setNhaSanXuat(String nhaSanXuat) {
        this.nhaSanXuat = nhaSanXuat;
    }

    public String getXuatXu() {
        return xuatXu;
    }

    public void setXuatXu(String xuatXu) {
        this.xuatXu = xuatXu;
    }

    public String getPhongBenh() {
        return phongBenh;
    }

    public void setPhongBenh(String phongBenh) {
        this.phongBenh = phongBenh;
    }

    public String getDoiTuong() {
        return doiTuong;
    }

    public void setDoiTuong(String doiTuong) {
        this.doiTuong = doiTuong;
    }

    public String getPhacDoTiem() {
        return phacDoTiem;
    }

    public void setPhacDoTiem(String phacDoTiem) {
        this.phacDoTiem = phacDoTiem;
    }

    public BigDecimal getGiaNiemYet() {
        return giaNiemYet;
    }

    public void setGiaNiemYet(BigDecimal giaNiemYet) {
        this.giaNiemYet = giaNiemYet;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public String getPhanLoai() {
        return phanLoai;
    }

    public void setPhanLoai(String phanLoai) {
        this.phanLoai = phanLoai;
    }

    public boolean isNoiBat() {
        return noiBat;
    }

    public void setNoiBat(boolean noiBat) {
        this.noiBat = noiBat;
    }
}
