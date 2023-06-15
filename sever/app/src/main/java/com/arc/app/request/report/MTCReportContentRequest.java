package com.arc.app.request.report;

import com.arc.app.entity.report.MTCReportContent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * author: NMDuc
 **/
@NoArgsConstructor
@Getter
@Setter
public class MTCReportContentRequest {
    private Long id;
    private ReportContentRequest reportContent;
    private Integer amount; // So luong

    public MTCReportContentRequest(MTCReportContent entity) {
        if (entity != null) {
            this.id = entity.getId();
            if (entity.getContent() != null) {
                this.reportContent = new ReportContentRequest(entity.getContent());
            }
            this.amount = entity.getAmount();
        }
    }

    public MTCReportContentRequest(Long id, String title, Integer indexNumber, Integer reportNumber, String subContent,
                                   Boolean bold, Boolean italics, Boolean canWrite, Long amount) {
        this.reportContent = new ReportContentRequest();
        this.reportContent.setId(id);
        this.reportContent.setTitle(title);
        this.reportContent.setIndexNumber(indexNumber);
        this.reportContent.setReportNumber(reportNumber);
        this.reportContent.setSubContent(subContent);
        this.reportContent.setBold(bold);
        this.reportContent.setItalics(italics);
        this.reportContent.setCanWrite(canWrite);
        this.amount = amount.intValue();
    }

    public Integer getIndexNumber() {
        if (this.reportContent != null && this.reportContent.getIndexNumber() != null) {
            return this.reportContent.getIndexNumber();
        }
        return null;
    }
}
