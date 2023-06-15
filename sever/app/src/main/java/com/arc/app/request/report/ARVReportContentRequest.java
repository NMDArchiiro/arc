package com.arc.app.request.report;

import com.arc.app.entity.report.ARVReportContent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * author: NMDuc
 **/
@NoArgsConstructor
@Getter
@Setter
public class ARVReportContentRequest {
    private Long id;
    private ReportContentRequest reportContent;
    private Integer numberMaleUnder15; // nam duoi 15
    private Integer numberFemaleUnder15;  // nu duoi 15
    private Integer numberTotalUnder15; // tong duoi 15
    private Integer numberMaleOver15; // nam tren 15
    private Integer numberFemaleOver15; // nu tren 15
    private Integer numberTotalOver15; // tong tren 15
    private Integer numberTotal; // tong

    public ARVReportContentRequest(ARVReportContent entity) {
        if (entity != null) {
            this.id = entity.getId();
            if (entity.getContent() != null) {
                this.reportContent = new ReportContentRequest(entity.getContent());
            }
            this.numberMaleUnder15 = entity.getNumberMaleUnder15();
            this.numberFemaleUnder15 = entity.getNumberFemaleUnder15();
            this.numberTotalUnder15 = entity.getNumberTotalUnder15();
            this.numberMaleOver15 = entity.getNumberMaleOver15();
            this.numberFemaleOver15 = entity.getNumberFemaleOver15();
            this.numberTotalOver15 = entity.getNumberTotalOver15();
            this.numberTotal = entity.getNumberTotal();
        }
    }

    public ARVReportContentRequest(Long id, String title, Integer indexNumber, Integer reportNumber, String subContent, Boolean bold, Boolean italics, Boolean canWrite,
                                   Long numberMaleUnder15, Long numberFemaleUnder15, Long numberTotalUnder15,
                                   Long numberMaleOver15, Long numberFemaleOver15, Long numberTotalOver15,
                                   Long numberTotal) {
        this.reportContent = new ReportContentRequest();
        this.reportContent.setId(id);
        this.reportContent.setTitle(title);
        this.reportContent.setIndexNumber(indexNumber);
        this.reportContent.setReportNumber(reportNumber);
        this.reportContent.setSubContent(subContent);
        this.reportContent.setBold(bold);
        this.reportContent.setItalics(italics);
        this.reportContent.setCanWrite(canWrite);
        this.numberMaleUnder15 = numberMaleUnder15.intValue();
        this.numberFemaleUnder15 = numberFemaleUnder15.intValue();
        this.numberTotalUnder15 = numberTotalUnder15.intValue();
        this.numberMaleOver15 = numberMaleOver15.intValue();
        this.numberFemaleOver15 = numberFemaleOver15.intValue();
        this.numberTotalOver15 = numberTotalOver15.intValue();
        this.numberTotal = numberTotal.intValue();
    }

    public Integer getIndexNumber() {
        if (this.reportContent != null && this.reportContent.getIndexNumber() != null) {
            return this.reportContent.getIndexNumber();
        }
        return null;
    }
}
