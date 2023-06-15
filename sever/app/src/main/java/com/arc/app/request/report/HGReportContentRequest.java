package com.arc.app.request.report;

import com.arc.app.entity.report.HGReportContent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * author: NMDuc
 **/
@NoArgsConstructor
@Getter
@Setter
public class HGReportContentRequest {
    private Long id;
    private ReportContentRequest reportContent;
    private Integer numberManage;// so quan ly
    private Integer numberEstimate;// so uoc tinh
    private String estimateMethod;// pp uoc tinh

    public HGReportContentRequest(HGReportContent entity) {
        if(entity != null) {
            this.id = entity.getId();
            if(entity.getContent() != null) {
                this.reportContent = new ReportContentRequest(entity.getContent());
            }
            this.numberManage = entity.getNumberManage();
            this.numberEstimate = entity.getNumberEstimate();
            this.estimateMethod = entity.getEstimateMethod();
        }
    }

    public HGReportContentRequest(Long id, String title, Integer indexNumber, Integer reportNumber, String subContent,
                                  Boolean bold, Boolean italics, Boolean canWrite,
                                  Long numberManage, Long numberEstimate) {
        this.reportContent = new ReportContentRequest();
        this.reportContent.setId(id);
        this.reportContent.setTitle(title);
        this.reportContent.setIndexNumber(indexNumber);
        this.reportContent.setReportNumber(reportNumber);
        this.reportContent.setSubContent(subContent);
        this.reportContent.setBold(bold);
        this.reportContent.setItalics(italics);
        this.reportContent.setCanWrite(canWrite);
        this.numberManage = numberManage.intValue();
        this.numberEstimate = numberEstimate.intValue();
    }
    public Integer getIndexNumber(){
        if(this.reportContent!=null && this.reportContent.getIndexNumber()!=null){
            return this.reportContent.getIndexNumber();
        }
        return null;
    }
}
