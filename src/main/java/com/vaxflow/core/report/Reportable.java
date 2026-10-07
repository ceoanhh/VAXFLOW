package com.vaxflow.core.report;

import java.time.LocalDate;
import java.util.List;

/**
 * Interface dung chung cho he thong bao cao da hinh (Polymorphism).
 * Cac module Hoa don, Nhan su, Vac xin implement interface nay.
 */
public interface Reportable {

    /**
     * Tao du lieu bao cao theo khoang thoi gian.
     *
     * @param fromDate Ngay bat dau loc
     * @param toDate   Ngay ket thuc loc
     * @return Danh sach cac dong bao cao (ReportRow) dung cho bieu do Chart.js hoac bang bieu
     */
    List<ReportRow> generateReport(LocalDate fromDate, LocalDate toDate);
}
