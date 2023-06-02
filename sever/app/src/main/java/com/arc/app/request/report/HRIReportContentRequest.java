package com.arc.app.request.report;

import com.arc.app.entity.report.HRIReportContent;
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
            this.syringeNeedle = entity.getSyringeNeedle();
            this.condom = entity.getCondom();
            this.lubricant = entity.getLubricant();
        }
    }

    public Integer getIndexNumber(){
        if(this.reportContent!=null && this.reportContent.getIndexNumber()!=null){
            return this.reportContent.getIndexNumber();
        }
        return null;
    }
}
