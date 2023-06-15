package com.arc.app.request.report;

import com.arc.app.entity.report.MDReportContent;
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
public class MDReportContentRequest {
    private Long id;
    private ReportContentRequest reportContent;
    private Integer numberMalePatient;
    private Integer numberFemalePatient;
    private Integer numberTotalPatient;

    public MDReportContentRequest(MDReportContent entity) {
        if(entity != null) {
            this.id = entity.getId();
            if(entity.getContent() != null) {
                this.reportContent = new ReportContentRequest(entity.getContent());
            }
            this.numberMalePatient = entity.getNumberMalePatient();
            this.numberFemalePatient = entity.getNumberFemalePatient();
            this.numberTotalPatient = entity.getNumberTotalPatient();
        }
    }

    public MDReportContentRequest(Long id, String title, Integer indexNumber, Integer reportNumber, String subContent, Boolean bold, Boolean italics,
                                     Boolean canWrite, Long numberMalePatient, Long numberFemalePatient, Long numberTotalPatient) {
        this.reportContent = new ReportContentRequest();
        this.reportContent.setId(id);
        this.reportContent.setTitle(title);
        this.reportContent.setIndexNumber(indexNumber);
        this.reportContent.setReportNumber(reportNumber);
        this.reportContent.setSubContent(subContent);
        this.reportContent.setBold(bold);
        this.reportContent.setItalics(italics);
        this.reportContent.setCanWrite(canWrite);
        this.numberMalePatient = numberMalePatient.intValue();
        this.numberFemalePatient = numberFemalePatient.intValue();
        this.numberTotalPatient = numberTotalPatient.intValue();
    }

    public Integer getIndexNumber(){
        if(this.reportContent!=null && this.reportContent.getIndexNumber()!=null){
            return this.reportContent.getIndexNumber();
        }
        return null;
    }
}
