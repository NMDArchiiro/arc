package com.arc.app.request.report;

import com.arc.app.entity.report.CReportContent;
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
public class CReportContentRequest {
    private Long id;
    private ReportContentRequest reportContent;
    private Integer numberNegative;// so luong am tinh
    private Integer numberPositive;// so luong duong tinh
    private Integer numberUnknown;// so luong khong xac dinh
    private Integer total;// tong

    public CReportContentRequest(CReportContent entity) {
        if(entity != null) {
            this.id = entity.getId();
            if(entity.getContent() != null) {
                this.reportContent = new ReportContentRequest(entity.getContent());
            }
            this.numberNegative = entity.getNumberNegative();
            this.numberPositive = entity.getNumberPositive();
            this.numberUnknown = entity.getNumberUnknown();
            this.total = entity.getTotal();
        }
    }

    public CReportContentRequest(Long id, String title, Integer indexNumber, Integer reportNumber, String subContent,
                                 Boolean bold, Boolean italics, Boolean canWrite,
                                 Long numberNegative, Long numberPositive, Long numberUnknown, Long total ){
        this.reportContent = new ReportContentRequest();
        this.reportContent.setId(id);
        this.reportContent.setTitle(title);
        this.reportContent.setIndexNumber(indexNumber);
        this.reportContent.setReportNumber(reportNumber);
        this.reportContent.setSubContent(subContent);
        this.reportContent.setBold(bold);
        this.reportContent.setItalics(italics);
        this.reportContent.setCanWrite(canWrite);
        this.numberNegative = numberNegative.intValue();
        this.numberPositive = numberPositive.intValue();
        this.numberUnknown = numberUnknown.intValue();
        this.total = total.intValue();
    }

    public Integer getIndexNumber(){
        if(this.reportContent!=null && this.reportContent.getIndexNumber()!=null){
            return this.reportContent.getIndexNumber();
        }
        return null;
    }
}
