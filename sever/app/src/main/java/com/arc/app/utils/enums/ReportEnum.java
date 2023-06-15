package com.arc.app.utils.enums;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * author: NMDuc
 **/
@Getter
@NoArgsConstructor
public enum ReportEnum {
    HRI(1, "Hoạt động can thiệp giảm tác hại"),
    ATH(2, "Tư vấn, xét nghiệm HIV"),
    POC(3, "Truyền thông phòng, chống HIV/AIDS"),
    MD(4, "Điều trị nghiện các chất dạng thuốc phiện bằng thuốc thay thế ( Methadone)"),
    ARV(5, "Quản lý điều trị ARV"),
    CI(6, "Quản lý điều trị đồng nhiễm HIV và lao"),
    MTC(7, "Dự phòng lây truyền HIV từ mẹ sang con"),
    PREP(8, "Dự phòng trước phơi nhiễm HIV (PrEP)"),
    C(9, "Chẩn đoán sớm nhiễm HIV cho trẻ dưới 18 tháng tuổi"),
    RPREP(10, "Duy trì điều trị dự phòng trước phơi nhiễm HIV (PrEP)"),
    CIHC(11, "Điều trị đồng nhiễm HIV và viêm gan C"),
    HG(12, "Số lượng đối tượng nguy cơ cao"),
    SP(13, "Điểm cung cấp dịch vụ"),
    IC(14, "Kinh phí triển khai các dịch vụ phòng, chống HIV/AIDS"),
    HI(15, "Bảo hiểm y tế");
    private Integer number;
    private String content;

    ReportEnum(Integer number, String content) {
        this.number = number;
        this.content = content;
    }
}
