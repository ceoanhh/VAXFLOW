package com.vaxflow.vaccine.dto;

public class BatchDropdownDTO {
    private Long id;
    private String maLo;
    private int soLuongConLai;
    private String hanSuDung;

    public BatchDropdownDTO(Long id, String maLo, int soLuongConLai, String hanSuDung) {
        this.id = id;
        this.maLo = maLo;
        this.soLuongConLai = soLuongConLai;
        this.hanSuDung = hanSuDung;
    }

    public Long getId() { return id; }
    public String getMaLo() { return maLo; }
    public int getSoLuongConLai() { return soLuongConLai; }
    public String getHanSuDung() { return hanSuDung; }
}
