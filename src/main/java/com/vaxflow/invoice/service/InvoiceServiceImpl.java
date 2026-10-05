package com.vaxflow.invoice.service;

import com.vaxflow.invoice.entity.HoaDonChiTiet;
import com.vaxflow.invoice.entity.HoaDonKhachHang;
import com.vaxflow.invoice.entity.PhuongThucThanhToan;
import com.vaxflow.invoice.entity.ThanhToan;
import com.vaxflow.invoice.entity.TrangThaiHoaDon;
import com.vaxflow.invoice.integration.GiaVaccineProvider;
import com.vaxflow.invoice.integration.HoSoTiemProvider;
import com.vaxflow.invoice.integration.HoSoTiemThongTin;
import com.vaxflow.invoice.repository.HoaDonChiTietRepository;
import com.vaxflow.invoice.repository.HoaDonKhachHangRepository;
import com.vaxflow.invoice.repository.ThanhToanRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final HoaDonKhachHangRepository hoaDonRepository;
    private final HoaDonChiTietRepository chiTietRepository;
    private final ThanhToanRepository thanhToanRepository;
    private final ObjectProvider<HoSoTiemProvider> hoSoTiemProvider;
    private final ObjectProvider<GiaVaccineProvider> giaVaccineProvider;

    public InvoiceServiceImpl(HoaDonKhachHangRepository hoaDonRepository,
            HoaDonChiTietRepository chiTietRepository,
            ThanhToanRepository thanhToanRepository,
            ObjectProvider<HoSoTiemProvider> hoSoTiemProvider,
            ObjectProvider<GiaVaccineProvider> giaVaccineProvider) {
        this.hoaDonRepository = hoaDonRepository;
        this.chiTietRepository = chiTietRepository;
        this.thanhToanRepository = thanhToanRepository;
        this.hoSoTiemProvider = hoSoTiemProvider;
        this.giaVaccineProvider = giaVaccineProvider;
    }

    @Override
    @Transactional
    public HoaDonKhachHang createInvoiceFromRecords(List<Long> vaccinationRecordIds) {
        validateRecordIds(vaccinationRecordIds);
        HoSoTiemProvider recordProvider = hoSoTiemProvider.getIfAvailable();
        if (recordProvider == null) {
            throw new InvoiceBusinessException("Chưa có kết nối hồ sơ tiêm từ Person 2.");
        }

        List<HoSoTiemThongTin> records = recordProvider.findRecords(vaccinationRecordIds);
        if (records == null || records.size() != vaccinationRecordIds.size()) {
            throw new InvoiceBusinessException("Không thể lập hóa đơn cho các hồ sơ không hợp lệ.");
        }

        validateRecords(vaccinationRecordIds, records);
        Long treatmentPlanId = records.get(0).getTreatmentPlanId();
        BigDecimal total = BigDecimal.ZERO;
        List<HoaDonChiTiet> lines = new java.util.ArrayList<>();

        for (HoSoTiemThongTin record : records) {
            BigDecimal unitPrice = resolveUnitPrice(record);
            int quantity = record.getQuantity();
            BigDecimal lineTotal = unitPrice.multiply(BigDecimal.valueOf(quantity));

            HoaDonChiTiet line = new HoaDonChiTiet();
            line.setIdTiem(record.getVaccinationRecordId());
            line.setSoLuong(quantity);
            line.setDonGia(unitPrice);
            line.setThanhTien(lineTotal);
            lines.add(line);
            total = total.add(lineTotal);
        }

        HoaDonKhachHang invoice = new HoaDonKhachHang();
        invoice.setMaHoaDon(createCode("HD"));
        // id_ho_so trong tài liệu được hiểu là mã liệu trình chung cho các mũi đã chọn.
        invoice.setIdHoSo(treatmentPlanId);
        invoice.setTongTien(total);
        invoice.setNgayLap(LocalDateTime.now());
        invoice.setTrangThai(TrangThaiHoaDon.CHUA_THANH_TOAN);
        invoice = hoaDonRepository.saveAndFlush(invoice);

        for (HoaDonChiTiet line : lines) {
            line.setIdHoaDon(invoice.getId());
        }
        chiTietRepository.saveAll(lines);
        return invoice;
    }

    @Transactional
    @Override
    public ThanhToan payInvoice(Long invoiceId, BigDecimal amount,
            PhuongThucThanhToan paymentMethod, Long cashierId) {
        if (invoiceId == null) {
            throw new InvoiceBusinessException("Mã hóa đơn không hợp lệ.");
        }
        HoaDonKhachHang invoice = hoaDonRepository.findByIdForUpdate(invoiceId)
                .orElseThrow(() -> new InvoiceBusinessException("Không tìm thấy hóa đơn."));
        if (invoice.getTrangThai() == TrangThaiHoaDon.DA_THANH_TOAN) {
            throw new InvoiceBusinessException("Hóa đơn đã được thanh toán.");
        }
        if (amount == null || amount.signum() <= 0) {
            throw new InvoiceBusinessException("Số tiền thanh toán phải lớn hơn 0.");
        }
        if (invoice.getTongTien() == null || amount.compareTo(invoice.getTongTien()) != 0) {
            throw new InvoiceBusinessException("Số tiền thanh toán phải bằng tổng tiền hóa đơn.");
        }
        if (paymentMethod == null) {
            throw new InvoiceBusinessException("Vui lòng chọn phương thức thanh toán.");
        }

        ThanhToan payment = new ThanhToan();
        payment.setMaThanhToan(createCode("TT"));
        payment.setIdHoaDon(invoice.getId());
        payment.setIdThuNgan(cashierId);
        payment.setSoTien(amount);
        payment.setNgayThu(LocalDateTime.now());
        payment.setPhuongThucTt(paymentMethod);
        thanhToanRepository.save(payment);

        invoice.setTrangThai(TrangThaiHoaDon.DA_THANH_TOAN);
        hoaDonRepository.save(invoice);
        return payment;
    }

    @Transactional(readOnly = true)
    @Override
    public List<HoaDonKhachHang> findAllInvoices() {
        return hoaDonRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public HoaDonKhachHang findInvoice(Long id) {
        return hoaDonRepository.findById(id)
                .orElseThrow(() -> new InvoiceBusinessException("Không tìm thấy hóa đơn."));
    }

    @Transactional(readOnly = true)
    @Override
    public List<HoaDonChiTiet> findInvoiceLines(Long id) {
        return chiTietRepository.findAllByIdHoaDonOrderByIdAsc(id);
    }

    private void validateRecordIds(List<Long> recordIds) {
        if (recordIds == null || recordIds.isEmpty()) {
            throw new InvoiceBusinessException("Vui lòng nhập ít nhất một mã hồ sơ tiêm.");
        }
        if (recordIds.stream().anyMatch(Objects::isNull)) {
            throw new InvoiceBusinessException("Mã hồ sơ tiêm không được để trống.");
        }
        if (new HashSet<>(recordIds).size() != recordIds.size()) {
            throw new InvoiceBusinessException("Danh sách có mã hồ sơ tiêm bị lặp.");
        }
        for (Long recordId : recordIds) {
            if (chiTietRepository.existsByIdTiem(recordId)) {
                throw new InvoiceBusinessException("Hồ sơ tiêm đã được lập hóa đơn.");
            }
        }
    }

    private void validateRecords(List<Long> requestedIds, List<HoSoTiemThongTin> records) {
        Long customerId = null;
        HashSet<Long> returnedIds = new HashSet<>();
        for (HoSoTiemThongTin record : records) {
            if (record == null || record.getVaccinationRecordId() == null
                    || !requestedIds.contains(record.getVaccinationRecordId())
                    || !returnedIds.add(record.getVaccinationRecordId())
                    || record.getCustomerId() == null || record.getTreatmentPlanId() == null
                    || record.getVaccineId() == null || record.getQuantity() == null
                    || record.getQuantity() <= 0 || !record.isEligibleForInvoice()) {
                throw new InvoiceBusinessException("Không thể lập hóa đơn cho các hồ sơ không hợp lệ.");
            }
            if (customerId == null) {
                customerId = record.getCustomerId();
            } else if (!customerId.equals(record.getCustomerId())) {
                throw new InvoiceBusinessException("Các hồ sơ tiêm phải thuộc cùng một khách hàng.");
            }
            if (!Objects.equals(records.get(0).getTreatmentPlanId(), record.getTreatmentPlanId())) {
                throw new InvoiceBusinessException("Các hồ sơ tiêm phải thuộc cùng một liệu trình.");
            }
            if (chiTietRepository.existsByIdTiem(record.getVaccinationRecordId())) {
                throw new InvoiceBusinessException("Hồ sơ tiêm đã được lập hóa đơn.");
            }
        }
    }

    private BigDecimal resolveUnitPrice(HoSoTiemThongTin record) {
        BigDecimal price = record.getUnitPrice();
        if (price == null) {
            GiaVaccineProvider priceProvider = giaVaccineProvider.getIfAvailable();
            if (priceProvider == null) {
                throw new InvoiceBusinessException("Chưa có nguồn đơn giá vaccine từ Person 2 hoặc Person 5.");
            }
            price = priceProvider.findUnitPrice(record.getVaccineId());
        }
        if (price == null || price.signum() < 0) {
            throw new InvoiceBusinessException("Đơn giá vaccine không hợp lệ.");
        }
        return price;
    }

    private String createCode(String prefix) {
        return prefix + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
