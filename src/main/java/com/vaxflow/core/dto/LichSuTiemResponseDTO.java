package com.vaxflow.core.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LichSuTiemResponseDTO {
    private Long id;
    private String maTiem;
    private Long idVaccine;
    private Long idLoVaccine;
    private Integer soMuiTiem;
    private LocalDate ngayTiem;
    private String trangThai;

    // Nhúng danh sách HoSoResponseDTO đã loại bỏ tham chiếu ngược
    private List<HoSoResponseDTO> hoSoList = new ArrayList<>();

    public LichSuTiemResponseDTO() {}

    // Getters & Setters ...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMaTiem() { return maTiem; }
    public void setMaTiem(String maTiem) { this.maTiem = maTiem; }

    public Long getIdVaccine() { return idVaccine; }
    public void setIdVaccine(Long idVaccine) { this.idVaccine = idVaccine; }

    public Long getIdLoVaccine() { return idLoVaccine; }
    public void setIdLoVaccine(Long idLoVaccine) { this.idLoVaccine = idLoVaccine; }

    public Integer getSoMuiTiem() { return soMuiTiem; }
    public void setSoMuiTiem(Integer soMuiTiem) { this.soMuiTiem = soMuiTiem; }

    public LocalDate getNgayTiem() { return ngayTiem; }
    public void setNgayTiem(LocalDate ngayTiem) { this.ngayTiem = ngayTiem; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }

    public List<HoSoResponseDTO> getHoSoList() { return hoSoList; }
    public void setHoSoList(List<HoSoResponseDTO> hoSoList) { this.hoSoList = hoSoList; }
}