package com.vaxflow.employee.entity;

public enum Position {
    GIAM_DOC("Giám đốc"),
    PHO_GIAM_DOC("Phó giám đốc"),
    TRUONG_PHONG("Trưởng phòng"),
    PHO_PHONG("Phó phòng"),
    BAC_SI("Bác sĩ"),
    DIEU_DUONG("Điều dưỡng"),
    NHAN_VIEN("Nhân viên");

    private final String description;
    Position(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
}
