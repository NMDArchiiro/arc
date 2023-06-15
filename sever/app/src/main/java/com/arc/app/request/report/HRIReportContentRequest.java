package com.arc.app.request.report;

import com.arc.app.entity.report.HRIReportContent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * author: NMDuc
 **/
@NoArgsConstructor
@Getter
@Setter
public class HRIReportContentRequest {
    private Long id;
    private ReportContentRequest reportContent;
    private Integer syringeNeedle; // Bom kim tiem
    private Integer condom; // Bao cao su
    private Integer lubricant; // Chat boi tron

    public HRIReportContentRequest(HRIReportContent entity) {
        if(entity != null) {
            this.id = entity.getId();
            if(entity.getContent() != null) {
                this.reportContent = new ReportContentRequest(entity.getContent());
            }
            this.syringeNeedle = entity.getNumberSyringeNeedle();
            this.condom = entity.getNumberCondom();
            this.lubricant = entity.getNumberLubricant();
        }
    }
    public HRIReportContentRequest(Long id, String title, Integer indexNumber, Integer reportNumber, Boolean canWrite, Long syringeNeedle, Long condom, Long lubricant) {
        this.reportContent = new ReportContentRequest();
        this.reportContent.setId(id);
        this.reportContent.setTitle(title);
        this.reportContent.setIndexNumber(indexNumber);
        this.reportContent.setReportNumber(reportNumber);
        this.reportContent.setCanWrite(canWrite);
        this.syringeNeedle = syringeNeedle.intValue();
        this.condom = condom.intValue();
        this.lubricant = lubricant.intValue();
    }

    public Integer getIndexNumber(){
        if(this.reportContent!=null && this.reportContent.getIndexNumber()!=null){
            return this.reportContent.getIndexNumber();
        }
        return null;
    }
}
