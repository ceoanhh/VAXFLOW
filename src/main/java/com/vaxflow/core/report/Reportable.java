package com.vaxflow.core.report;

import java.time.LocalDate;
import java.util.List;

/**
 * Interface dung chung cho phan bao cao theo da hinh (Polymorphism).
 * Cac module Invoice (Person 3), Employee (Person 4), Vaccine (Person 5) se implement interface nay.
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
