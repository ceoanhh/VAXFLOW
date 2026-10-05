/**
 * VAXFLOW - Client Portal Interactive JavaScript
 */

document.addEventListener('DOMContentLoaded', function () {
    // 1. Booking Wizard Stepper Handling
    const step1 = document.getElementById('step-1-content');
    const step2 = document.getElementById('step-2-content');
    const step3 = document.getElementById('step-3-content');

    const step1Indicator = document.getElementById('step-indicator-1');
    const step2Indicator = document.getElementById('step-indicator-2');
    const step3Indicator = document.getElementById('step-indicator-3');

    window.goToStep = function (stepNumber) {
        if (!step1 || !step2 || !step3) return;

        // Step 1 -> Step 2 simple validation check
        if (stepNumber === 2) {
            const hoTen = document.getElementById('hoTenNguoiTiem');
            const sdt = document.getElementById('soDienThoai');
            if (hoTen && !hoTen.value.trim()) {
                alert('Vui lòng nhập Họ và tên người tiêm!');
                hoTen.focus();
                return;
            }
            if (sdt && !sdt.value.trim()) {
                alert('Vui lòng nhập Số điện thoại liên hệ!');
                sdt.focus();
                return;
            }
        }

        // Hide all steps
        step1.classList.add('d-none');
        step2.classList.add('d-none');
        step3.classList.add('d-none');

        step1Indicator.classList.remove('active', 'completed');
        step2Indicator.classList.remove('active', 'completed');
        step3Indicator.classList.remove('active', 'completed');

        if (stepNumber === 1) {
            step1.classList.remove('d-none');
            step1Indicator.classList.add('active');
        } else if (stepNumber === 2) {
            step2.classList.remove('d-none');
            step1Indicator.classList.add('completed');
            step2Indicator.classList.add('active');
        } else if (stepNumber === 3) {
            step3.classList.remove('d-none');
            step1Indicator.classList.add('completed');
            step2Indicator.classList.add('completed');
            step3Indicator.classList.add('active');
            updateReviewSummary();
        }

        window.scrollTo({ top: 250, behavior: 'smooth' });
    };

    function updateReviewSummary() {
        const hoTen = document.getElementById('hoTenNguoiTiem')?.value || '---';
        const sdt = document.getElementById('soDienThoai')?.value || '---';
        const vacXin = document.getElementById('tenVacXin')?.value || '---';
        const coSo = document.getElementById('coSoTiem')?.value || '---';
        const ngay = document.getElementById('ngayHenTiem')?.value || '---';
        const gio = document.getElementById('khungGioHen')?.value || '---';

        const sumHoTen = document.getElementById('summary-hoTen');
        const sumSdt = document.getElementById('summary-sdt');
        const sumVacXin = document.getElementById('summary-vacXin');
        const sumCoSo = document.getElementById('summary-coSo');
        const sumNgay = document.getElementById('summary-ngay');

        if (sumHoTen) sumHoTen.innerText = hoTen;
        if (sumSdt) sumSdt.innerText = sdt;
        if (sumVacXin) sumVacXin.innerText = vacXin;
        if (sumCoSo) sumCoSo.innerText = coSo;
        if (sumNgay) sumNgay.innerText = ngay + ' (' + gio + ')';
    }

    // 2. Vaccine Fast Filter by Search
    const liveSearchInput = document.getElementById('vaccineLiveSearch');
    if (liveSearchInput) {
        liveSearchInput.addEventListener('keyup', function () {
            const query = this.value.toLowerCase().trim();
            const cards = document.querySelectorAll('.vaccine-item-col');
            cards.forEach(card => {
                const text = card.textContent.toLowerCase();
                if (text.includes(query)) {
                    card.style.display = '';
                } else {
                    card.style.display = 'none';
                }
            });
        });
    }

    // 3. Quick fill demo keyword in Lookup page
    window.fillLookupKeyword = function (keyword) {
        const input = document.getElementById('lookupKeywordInput');
        if (input) {
            input.value = keyword;
            input.form.submit();
        }
    };

    // 4. Print Certificate
    window.printVaccinationPass = function () {
        window.print();
    };

    // 5. Copy text helper
    window.copyToClipboard = function (text) {
        navigator.clipboard.writeText(text).then(function () {
            alert('Đã sao chép mã đặt lịch: ' + text);
        }).catch(function () {
            prompt('Mã đặt lịch của bạn:', text);
        });
    };
});
