package com.arc.app.request.report;

import com.arc.app.entity.report.ReportContent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

/**
 * author: NMDuc
 **/
@NoArgsConstructor
@Getter
@Setter
public class ReportContentRequest {
    private Long id;
    private String title;
    private Integer indexNumber;
    private Integer reportNumber;
    private String subContent;
    private Boolean canWrite;
    private Boolean bold;
    private Boolean italics;
    private List<Integer> sumOfIndexNumber;

    public ReportContentRequest(ReportContent entity) {
        if(entity != null) {
            this.id = entity.getId();
            this.title = entity.getTitle();
            this.indexNumber = entity.getIndexNumber();
            this.reportNumber = entity.getReportNumber();
            this.subContent = entity.getSubContent();
            this.canWrite = entity.getCanWrite();
            this.bold = entity.getBold();
            this.italics = entity.getItalics();
            this.sumOfIndexNumber = entity.getSumOfIndexNumber();
        }
    }
}
