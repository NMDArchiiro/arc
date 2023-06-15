package com.arc.app.request.report;

import com.arc.app.entity.report.ICReportContent;
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
public class ICReportContentRequest {
    private Long id;
    private ReportContentRequest reportContent;
    private Integer provision; // Chi phi du phong
    private Integer treatment; // Chi phi dieu tri
    private Integer latest; // Chi phi xet nghiem
    private Integer reviews; // Chi phi theo doi danh gia
    private Integer capacityBuilding; // Chi phi nang cao nang luc

    public ICReportContentRequest(ICReportContent entity) {
        if(entity != null) {
            this.id = entity.getId();
            if(entity.getContent() != null) {
                this.reportContent = new ReportContentRequest(entity.getContent());
            }
            this.provision = entity.getProvision();
            this.treatment = entity.getTreatment();
            this.latest = entity.getLatest();
            this.reviews = entity.getReviews();
            this.capacityBuilding = entity.getCapacityBuilding();
        }
    }

    public ICReportContentRequest(Long id, String title, Integer indexNumber, Integer reportNumber, String subContent,
                                  Boolean bold, Boolean italics, Boolean canWrite,
                                  Long provision, Long treatment, Long latest, Long reviews, Long capacityBuilding){
        this.reportContent = new ReportContentRequest();
        this.reportContent.setId(id);
        this.reportContent.setTitle(title);
        this.reportContent.setIndexNumber(indexNumber);
        this.reportContent.setReportNumber(reportNumber);
        this.reportContent.setSubContent(subContent);
        this.reportContent.setBold(bold);
        this.reportContent.setItalics(italics);
        this.reportContent.setCanWrite(canWrite);
        this.provision = provision.intValue();
        this.treatment = treatment.intValue();
        this.latest = latest.intValue();
        this.reviews = reviews.intValue();
        this.capacityBuilding = capacityBuilding.intValue();
    }

    public Integer getIndexNumber(){
        if(this.reportContent!=null && this.reportContent.getIndexNumber()!=null){
            return this.reportContent.getIndexNumber();
        }
        return null;
    }
}
