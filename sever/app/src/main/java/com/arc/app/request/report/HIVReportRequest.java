package com.arc.app.request.report;

import com.arc.app.entity.report.HIVReport;
import com.arc.app.request.base.AdminUnitRequest;
import com.arc.app.request.base.HealthOrganizationRequest;
import com.arc.app.utils.constants.HIVConstants;
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
            if(entity.getAdminUnit() != null && entity.getAdminUnit().getLevel() != null) {
                switch (entity.getAdminUnit().getLevel()) {
                    case HIVConstants.LEVEL_PROVINCE:
                        this.province = new AdminUnitRequest(entity.getAdminUnit(), false);
                        break;
                    case HIVConstants.LEVEL_DISTRICT:
                        this.district = new AdminUnitRequest(entity.getAdminUnit(), false);
                        if(entity.getAdminUnit().getParent() != null) {
                            this.province = new AdminUnitRequest(entity.getAdminUnit().getParent(), false);
                        }
                        break;
                    case HIVConstants.LEVEL_COMMUNE:
                        this.commune = new AdminUnitRequest(entity.getAdminUnit(), false);
                        if(entity.getAdminUnit().getParent() != null) {
                            this.district = new AdminUnitRequest(entity.getAdminUnit().getParent(), false);
                            if(entity.getAdminUnit().getParent().getParent() != null) {
                                this.province = new AdminUnitRequest(entity.getAdminUnit().getParent().getParent(), false);
                            }
                        }
                        break;
                }
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
