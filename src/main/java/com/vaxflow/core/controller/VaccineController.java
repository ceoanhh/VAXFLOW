package com.vaxflow.core.controller;

import com.vaxflow.core.dto.VaccineDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller quan ly danh muc vac-xin cho Admin
 * Route prefix: /vaccines
 */
@Controller
@RequestMapping("/vaccines")
public class VaccineController {

    private static final List<VaccineDto> VACCINE_LIST = new ArrayList<>();

    static {
        VACCINE_LIST.add(new VaccineDto(
                "VAC-01", "Hexaxim 6 trong 1", "Sanofi Pasteur", "Pháp",
                "Bạch hầu, ho gà, uốn ván, bại liệt, Hib, viêm gan B",
                "Trẻ từ 2 tháng đến 24 tháng tuổi", "Phác đồ 3+1 mũi",
                new BigDecimal("1050000"), "CON_HANG", "TRE_EM", true
        ));
        VACCINE_LIST.add(new VaccineDto(
                "VAC-02", "Synflorix (Phế cầu 10)", "GSK", "Bỉ",
                "Viêm phổi, viêm màng não, viêm tai giữa do phế cầu",
                "Trẻ từ 6 tuần đến 5 tuổi", "Phác đồ 3+1 mũi",
                new BigDecimal("1045000"), "CON_HANG", "TRE_EM", true
        ));
        VACCINE_LIST.add(new VaccineDto(
                "VAC-03", "Prevenar 13 (Phế cầu 13)", "Pfizer", "Bỉ / Mỹ",
                "13 chủng phế cầu khuẩn gây viêm phổi, nhiễm trùng huyết",
                "Trẻ từ 6 tuần & người lớn", "Trẻ nhỏ: 3+1 mũi; Người lớn: 1 mũi",
                new BigDecimal("1290000"), "CON_HANG", "TAT_CA", true
        ));
        VACCINE_LIST.add(new VaccineDto(
                "VAC-04", "Gardasil 9 (HPV 9 chủng)", "MSD", "Mỹ",
                "Ung thư cổ tử cung, ung thư hậu môn, mụn cóc sinh dục",
                "Nam & Nữ từ 9 tuổi đến 45 tuổi", "2 hoặc 3 mũi theo độ tuổi",
                new BigDecimal("2950000"), "CON_HANG", "TIEN_HON_NHAN", true
        ));
        VACCINE_LIST.add(new VaccineDto(
                "VAC-05", "Influvac Tetra (Cúm mùa)", "Abbott", "Hà Lan",
                "4 chủng cúm mùa nguy hiểm (A/H1N1, A/H3N2, B)",
                "Trẻ từ 6 tháng tuổi và người lớn", "Tiêm nhắc lại hàng năm",
                new BigDecimal("350000"), "CON_HANG", "TAT_CA", true
        ));
        VACCINE_LIST.add(new VaccineDto(
                "VAC-06", "Imojev (Viêm não Nhật Bản)", "Sanofi", "Thái Lan",
                "Phòng bệnh viêm não Nhật Bản",
                "Trẻ từ 9 tháng & người lớn", "Trẻ em: 2 mũi; Người lớn: 1 mũi",
                new BigDecimal("720000"), "CON_HANG", "TAT_CA", false
        ));
        VACCINE_LIST.add(new VaccineDto(
                "VAC-07", "Varilrix (Thủy đậu)", "GSK", "Bỉ",
                "Bệnh thủy đậu và các biến chứng nhiễm trùng da, viêm não",
                "Trẻ từ 9 tháng & người lớn", "Phác đồ 2 mũi",
                new BigDecimal("920000"), "CON_HANG", "TRE_EM", false
        ));
        VACCINE_LIST.add(new VaccineDto(
                "VAC-08", "Boostrix (Bạch hầu - Uốn ván - Ho gà nhắc)", "GSK", "Bỉ",
                "Mũi tiêm nhắc bạch hầu, uốn ván, ho gà",
                "Trẻ từ 4 tuổi, phụ nữ có thai và người lớn", "1 mũi duy nhất, nhắc mỗi 10 năm",
                new BigDecimal("790000"), "CON_HANG", "TIEN_HON_NHAN", false
        ));
    }

    @GetMapping
    public String listVaccines(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<VaccineDto> filtered = VACCINE_LIST.stream()
                .filter(v -> {
                    if (keyword == null || keyword.trim().isEmpty()) return true;
                    String kw = keyword.trim().toLowerCase();
                    return v.getTenVacXin().toLowerCase().contains(kw)
                            || v.getPhongBenh().toLowerCase().contains(kw)
                            || v.getXuatXu().toLowerCase().contains(kw);
                })
                .collect(Collectors.toList());

        model.addAttribute("vaccines", filtered);
        model.addAttribute("keyword", keyword);
        return "vaccines/list";
    }
}
