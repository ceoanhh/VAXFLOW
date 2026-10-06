package com.vaxflow.vaccine.entity;

import com.vaxflow.core.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "vac_xin")
public class VacXin extends BaseEntity {

    @Column(name = "ma_vac_xin", length = 50, nullable = false, unique = true)
    private String maVacXin;

    @Column(name = "ten_vac_xin", length = 150, nullable = false)
    private String tenVacXin;

    @Column(name = "nha_san_xuat", length = 150, nullable = false)
    private String nhaSanXuat;

    @Column(name = "loai_benh_phong", length = 255, nullable = false)
    private String loaiBenhPhong;

    @Column(name = "so_mui_can_tiem", nullable = false)
    private int soMuiCanTiem;

    @Column(name = "khoang_cach_ngay")
    private Integer khoangCachNgay; // Có thể null nên dùng Integer

    @Column(name = "don_gia", precision = 15, scale = 0, nullable = false)
    private BigDecimal donGia;

}
