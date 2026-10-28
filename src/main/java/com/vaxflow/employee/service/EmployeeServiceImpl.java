package com.vaxflow.employee.service;

import com.vaxflow.employee.dto.EmployeeDTO;
import com.vaxflow.employee.entity.Department;
import com.vaxflow.employee.entity.Employee;
import com.vaxflow.employee.entity.Position;
import com.vaxflow.employee.repository.DepartmentRepository;
import com.vaxflow.employee.repository.EmployeeRepository;
import com.vaxflow.employee.repository.PayrollRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final PayrollRepository payrollRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository, PayrollRepository payrollRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.payrollRepository = payrollRepository;
    }

    @Override
    public List<EmployeeDTO> getAll() {
        return employeeRepository.findAll().stream().map(this::toDTO).toList();
    }

    @Override
    public EmployeeDTO getById(Long id) {
        return toDTO(findEntityById(id));
    }

    @Override
    public EmployeeDTO create(EmployeeDTO employeeDTO) {
        validateDuLieuDuyNhat(employeeDTO, null);
        validateQuyTacNhanSu(employeeDTO);
        Employee employee = new Employee();
        employee.setMaNhanSu("VAX");
        copyToEntity(employeeDTO, employee);
        employee = employeeRepository.save(employee);
        employee.setMaNhanSu(String.format("VAX%03d", employee.getId()));
        return toDTO(employeeRepository.save(employee));
    }

    @Override
    public EmployeeDTO update(Long id, EmployeeDTO employeeDTO) {
        validateQuyTacNhanSu(employeeDTO);
        validateDuLieuDuyNhat(employeeDTO, id);
        Employee employee = findEntityById(id);
        copyToEntity(employeeDTO, employee);
        return toDTO(employeeRepository.save(employee));
    }

    @Override
    public void delete(Long id) {
        payrollRepository.deleteAllByNhanSuId(id);
        employeeRepository.delete(findEntityById(id));
    }

    @Override
    public List<EmployeeDTO> search(String keyword, Long departmentId) {
        List<EmployeeDTO> employees = getAll();
        //Lọc theo từ khóa
        if (keyword != null && !keyword.trim().isEmpty()) {
            String kw = keyword.trim().toLowerCase();
            employees = employees.stream()
                    .filter(e -> (e.getHoVaTen() != null && e.getHoVaTen().toLowerCase().contains(kw))
                            || (e.getMaNhanSu() != null && e.getMaNhanSu().toLowerCase().contains(kw))
                            || (e.getSdt() != null && e.getSdt().contains(kw))
                            || (e.getCccd() != null && e.getCccd().contains(kw)))
                    .toList();
        }
        //Lọc theo ID phòng ban
        if (departmentId != null) {
            employees = employees.stream()
                    .filter(e -> e.getIdPhongBan() != null && e.getIdPhongBan().equals(departmentId))
                    .toList();
        }
        return employees;
    }

    @Override
    public Optional<EmployeeDTO> findByMaNhanSu(String maNhanSu) {
        return employeeRepository.findByMaNhanSu(maNhanSu).map(this::toDTO);
    }

    @Override  
    public boolean existsByMaNhanSu(String maNhanSu) {
        return employeeRepository.existsByMaNhanSu(maNhanSu);
    }

    private Employee findEntityById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Khong tim thay nhan su voi id: " + id));
    }

    private Department findDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Khong tim thay phong ban voi id: " + id));
    }

    // 3. THÊM HÀM LOGIC NÀY VÀO CUỐI CLASS
    private void validateDuLieuDuyNhat(EmployeeDTO dto, Long id) {
        // Nếu là Update, lấy bản ghi cũ lên để đối chiếu (tránh việc tự báo trùng với chính mình)
        Employee oldEmp = (id != null) ? findEntityById(id) : null;

        // 1. Kiểm tra CCCD
        if (employeeRepository.existsByCccd(dto.getCccd())) {
            // Lỗi nếu: Thêm mới (oldEmp == null) HOẶC sửa hồ sơ nhưng CCCD mới gõ khác với CCCD gốc đang lưu
            if (oldEmp == null || !oldEmp.getCccd().equals(dto.getCccd())) {
                throw new RuntimeException("Lỗi: Số CCCD/CMND này đã được đăng ký!");
            }
        }

        // 2. Kiểm tra Số Điện Thoại
        if (employeeRepository.existsBySdt(dto.getSdt())) {
            if (oldEmp == null || !oldEmp.getSdt().equals(dto.getSdt())) {
                throw new RuntimeException("Lỗi: Số điện thoại này đã tồn tại trong hệ thống!");
            }
        }

        // 3. Kiểm tra Email (Chỉ kiểm tra nếu người dùng có nhập, vì email thường cho phép để trống)
        if (dto.getEmail() != null && !dto.getEmail().trim().isEmpty()) {
            if (employeeRepository.existsByEmail(dto.getEmail())) {
                if (oldEmp == null || !dto.getEmail().equals(oldEmp.getEmail())) {
                    throw new RuntimeException("Lỗi: Địa chỉ email này đã được sử dụng!");
                }
            }
        }
    }

    // Đừng quên @Autowired DepartmentRepository nếu chưa có
    private void validateQuyTacNhanSu(EmployeeDTO dto) {
        // Lấy tên phòng ban từ ID
        Department dept = departmentRepository.findById(dto.getIdPhongBan())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phòng ban"));
        String tenPhong = dept.getTen().toLowerCase();
        Position viTri = dto.getChucVu();

        // 1. KIỂM TRA SAI TUYẾN (Phòng nào đi với chức vụ đó)
        if (tenPhong.contains("ban giám đốc")) {
            if (viTri != Position.GIAM_DOC && viTri != Position.PHO_GIAM_DOC) {
                throw new RuntimeException("Lỗi: Ban Giám đốc chỉ được chọn chức vụ Giám đốc hoặc Phó Giám đốc!");
            }
        } else if (tenPhong.contains("phòng khám")) {
            if (viTri == Position.GIAM_DOC || viTri == Position.PHO_GIAM_DOC || viTri == Position.NHAN_VIEN) {
                throw new RuntimeException("Lỗi: Phòng khám chỉ gồm Trưởng/Phó phòng, Bác sĩ và Điều dưỡng!");
            }
        } else {
            // Các phòng Dược, Kế toán, Kỹ thuật, Hành chính...
            if (viTri != Position.TRUONG_PHONG && viTri != Position.PHO_PHONG && viTri != Position.NHAN_VIEN) {
                throw new RuntimeException("Lỗi: Phòng ban này chỉ gồm Trưởng/Phó phòng và Nhân viên thường!");
            }
        }

        // 2. KIỂM TRA SỐ LƯỢNG (Giới hạn "ghế")
        long soLuongHienTai = employeeRepository.countByPhongBanIdAndChucVu(dept.getId(), viTri);
        
        // Nếu là thao tác Cập nhật (Edit) và chính nhân viên này đang ngồi ghế đó thì trừ đi 1 để không tự chặn mình
        if (dto.getId() != null) {
            Employee oldEmp = employeeRepository.findById(dto.getId()).orElse(null);
            if (oldEmp != null && oldEmp.getPhongBan().getId().equals(dept.getId()) && oldEmp.getChucVu() == viTri) {
                soLuongHienTai--; 
            }
        }

        // Áp dụng luật số lượng
        if (viTri == Position.GIAM_DOC && soLuongHienTai >= 1) {
            throw new RuntimeException("Lỗi: Đã có 1 Giám đốc, không thể thêm mới!");
        }
        if (viTri == Position.PHO_GIAM_DOC && soLuongHienTai >= 2) {
            throw new RuntimeException("Lỗi: Đã đủ 2 Phó Giám đốc!");
        }
        if (viTri == Position.TRUONG_PHONG && soLuongHienTai >= 1) {
            throw new RuntimeException("Lỗi: Phòng này đã có Trưởng phòng!");
        }
        if (viTri == Position.PHO_PHONG && soLuongHienTai >= 1) {
            throw new RuntimeException("Lỗi: Phòng này đã có Phó phòng!");
        }
    }

    private void copyToEntity(EmployeeDTO dto, Employee entity) {
        entity.setHoVaTen(dto.getHoVaTen());
        entity.setCccd(dto.getCccd());
        entity.setNgaySinh(dto.getNgaySinh());
        entity.setGioiTinh(dto.getGioiTinh());
        entity.setDiaChi(dto.getDiaChi());
        entity.setSdt(dto.getSdt());
        entity.setEmail(dto.getEmail());
        entity.setChucVu(dto.getChucVu());
        entity.setNgayVaoLam(dto.getNgayVaoLam());
        entity.setLuongCung(dto.getLuongCung());
        entity.setPhongBan(findDepartmentById(dto.getIdPhongBan()));
    }

    private EmployeeDTO toDTO(Employee entity) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(entity.getId());
        dto.setMaNhanSu(entity.getMaNhanSu());
        dto.setHoVaTen(entity.getHoVaTen());
        dto.setCccd(entity.getCccd());
        dto.setNgaySinh(entity.getNgaySinh());
        dto.setGioiTinh(entity.getGioiTinh());
        dto.setDiaChi(entity.getDiaChi());
        dto.setSdt(entity.getSdt());
        dto.setEmail(entity.getEmail());
        dto.setChucVu(entity.getChucVu());
        dto.setNgayVaoLam(entity.getNgayVaoLam());
        dto.setLuongCung(entity.getLuongCung());
        dto.setIdPhongBan(entity.getPhongBan() != null ? entity.getPhongBan().getId() : null);
        dto.setTenPhongBan(entity.getPhongBan() != null ? entity.getPhongBan().getTen() : null);
        dto.setThoiGianTao(entity.getThoiGianTao());
        dto.setThoiGianCapNhat(entity.getThoiGianCapNhat());
        return dto;
    }
}
