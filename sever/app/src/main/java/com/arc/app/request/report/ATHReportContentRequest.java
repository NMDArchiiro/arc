package com.arc.app.request.report;

import com.arc.app.entity.report.ATHReportContent;
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
public class ATHReportContentRequest {
    private Long id;
    private ReportContentRequest reportContent;
    private Integer numberAdviseMale; // So luot duong tinh hiv nam
    private Integer numberAdviseFemale; // So luot duong hiv nu
    private Integer numberAdviseTotal; // tong so luot duong tinh hiv
    private Integer numberTestingMale; // so luot xet nghiem nam
    private Integer numberTestingFemale; // so luot xet nghiem nu
    private Integer numberTestingTotal; // tong so luot xet nghiem
    private Integer numberPersonAdviseMale; // so nguoi hiv nam
    private Integer numberPersonAdviseFemale; // so nguoi hiv nu
    private Integer numberPersonAdviseTotal; // tong so nguoi hiv
    private Integer numberPersonTestingMale; // so nguoi xet nghiem nam
    private Integer numberPersonTestingFemale; // so nguoi xet nghiem nu
    private Integer numberPersonTestingTotal; // tong so nguoi xet nghiem

    public ATHReportContentRequest(ATHReportContent entity) {
        if(entity != null) {
            this.id = entity.getId();
            if(entity.getContent() != null) {
                this.reportContent = new ReportContentRequest(entity.getContent());
            }
            this.numberAdviseMale = entity.getNumberAdviseMale();
            this.numberAdviseFemale = entity.getNumberAdviseFemale();
            this.numberAdviseTotal = entity.getNumberAdviseTotal();
            this.numberTestingMale = entity.getNumberTestingMale();
            this.numberTestingFemale = entity.getNumberTestingFemale();
            this.numberTestingTotal = entity.getNumberTestingTotal();
            this.numberPersonAdviseMale = entity.getNumberPersonAdviseMale();
            this.numberPersonAdviseFemale = entity.getNumberPersonAdviseFemale();
            this.numberPersonAdviseTotal = entity.getNumberPersonAdviseTotal();
            this.numberPersonTestingMale = entity.getNumberPersonTestingMale();
            this.numberPersonTestingFemale = entity.getNumberPersonTestingFemale();
            this.numberPersonTestingTotal = entity.getNumberPersonTestingTotal();
        }
    }

    public ATHReportContentRequest(Long id, String title, Integer indexNumber, Integer reportNumber, String subContent, Boolean bold, Boolean italics, Boolean canWrite,
                                            Long numberAdviseMale, Long numberAdviseFemale, Long numberAdviseTotal,
                                            Long numberTestingMale, Long numberTestingFemale, Long numberTestingTotal,
                                            Long numberPersonAdviseMale, Long numberPersonAdviseFemale, Long numberPersonAdviseTotal,
                                            Long numberPersonTestingMale, Long numberPersonTestingFemale, Long numberPersonTestingTotal) {
        this.reportContent = new ReportContentRequest();
        this.reportContent.setId(id);
        this.reportContent.setTitle(title);
        this.reportContent.setIndexNumber(indexNumber);
        this.reportContent.setReportNumber(reportNumber);
        this.reportContent.setSubContent(subContent);
        this.reportContent.setBold(bold);
        this.reportContent.setItalics(italics);
        this.reportContent.setCanWrite(canWrite);
        this.numberAdviseMale = numberAdviseMale.intValue();
        this.numberAdviseFemale = numberAdviseFemale.intValue();
        this.numberAdviseTotal = numberAdviseTotal.intValue();
        this.numberTestingMale = numberTestingMale.intValue();
        this.numberTestingFemale = numberTestingFemale.intValue();
        this.numberTestingTotal = numberTestingTotal.intValue();
        this.numberPersonAdviseMale = numberPersonAdviseMale.intValue();
        this.numberPersonAdviseFemale = numberPersonAdviseFemale.intValue();
        this.numberPersonAdviseTotal = numberPersonAdviseTotal.intValue();
        this.numberPersonTestingMale = numberPersonTestingMale.intValue();
        this.numberPersonTestingFemale = numberPersonTestingFemale.intValue();
        this.numberPersonTestingTotal = numberPersonTestingTotal.intValue();
    }

    public Integer getIndexNumber(){
        if(this.reportContent!=null && this.reportContent.getIndexNumber()!=null){
            return this.reportContent.getIndexNumber();
        }
        return null;
    }
}
