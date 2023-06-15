package com.arc.app.request.report;

import com.arc.app.entity.report.RPREPReportContent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * author: NMDuc
 **/
@NoArgsConstructor
@Getter
@Setter
public class RPREPReportContentRequest {
    private Long id;
    private ReportContentRequest reportContent;
    private Integer numberNCMT; // Nghien trich ma tuy
    private Integer numberMSM; // Quan he tinh duc dong gioi nam
    private Integer numberPNBD; // Phu nu ban dam
    private Integer numberTG; // Nguoi chuyen doi gioi tinh
    private Integer numberOther; // Khac
    private Integer numberTotal; // Tong

    public RPREPReportContentRequest(RPREPReportContent entity) {
        if (entity != null) {
            this.id = entity.getId();
            if (entity.getContent() != null) {
                this.reportContent = new ReportContentRequest(entity.getContent());
            }
            this.numberNCMT = entity.getNumberNCMT();
            this.numberMSM = entity.getNumberMSM();
            this.numberPNBD = entity.getNumberPNBD();
            this.numberTG = entity.getNumberTG();
            this.numberOther = entity.getNumberOther();
            this.numberTotal = entity.getNumberTotal();
        }
    }

    public RPREPReportContentRequest(Long id, String title, Integer indexNumber, Integer reportNumber, String subContent,
                                    Boolean bold, Boolean italics, Boolean canWrite, Long numberNCMT,
                                    Long numberMSM, Long numberPNBD, Long numberTG, Long numberOther, Long numberTotal) {
        this.reportContent = new ReportContentRequest();
        this.reportContent.setId(id);
        this.reportContent.setTitle(title);
        this.reportContent.setIndexNumber(indexNumber);
        this.reportContent.setReportNumber(reportNumber);
        this.reportContent.setSubContent(subContent);
        this.reportContent.setBold(bold);
        this.reportContent.setItalics(italics);
        this.reportContent.setCanWrite(canWrite);
        this.numberNCMT = numberNCMT.intValue();
        this.numberMSM = numberMSM.intValue();
        this.numberPNBD = numberPNBD.intValue();
        this.numberTG = numberTG.intValue();
        this.numberOther = numberOther.intValue();
        this.numberTotal = numberTotal.intValue();
    }

    public Integer getIndexNumber() {
        if (this.reportContent != null && this.reportContent.getIndexNumber() != null) {
            return this.reportContent.getIndexNumber();
        }
        return null;
    }
}
