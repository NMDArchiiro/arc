package com.arc.app.request.report;

import com.arc.app.entity.report.HIVReport;
import com.arc.app.request.base.AdminUnitRequest;
import com.arc.app.request.base.HealthOrganizationRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * author: NMDuc
 **/
@Getter
@Setter
@NoArgsConstructor
public class HIVReportRequest {
    private Long id;
    private Integer quarter;
    private Integer year;
    private Integer status;
    private String note;
    private Boolean locked;
    private Date modifyDate;
    private AdminUnitRequest province;
    private AdminUnitRequest district;
    private AdminUnitRequest commune;
    private HealthOrganizationRequest healthOrg;
    private FileDescriptionRequest file;
    private HRIReportRequest hriReport; // Bao cao 1
    private String quarterYear;
    private List<NotificationReport> notifications;
    private Boolean canEdit;
    private Boolean canView;
    private Boolean canConfirm;
    private Boolean canConfirmSame;

    public HIVReportRequest(HIVReport entity) {
        if(entity != null) {
            this.id = entity.getId();
            this.quarter = entity.getQuarter();
            this.year = entity.getYear();
            this.status = entity.getStatus();
            this.note = entity.getNote();
            this.locked = entity.getLocked();
            this.modifyDate = entity.getLastModifiedDate();
            if(entity.getProvince() != null) {
                this.province = new AdminUnitRequest(entity.getProvince(),false);
            }
            if(entity.getDistrict() != null) {
                this.province = new AdminUnitRequest(entity.getDistrict(),false);
            }
            if(entity.getCommune() != null) {
                this.province = new AdminUnitRequest(entity.getCommune(),false);
            }
            if(entity.getHealthOrg() != null) {
                this.healthOrg = new HealthOrganizationRequest(entity.getHealthOrg(),false);
            }
            if(entity.getFile() != null) {
                this.file = new FileDescriptionRequest(entity.getFile());
            }
            if(entity.getHriReport() != null) {
                this.hriReport = new HRIReportRequest(entity.getHriReport());
            }
        }
    }

}
