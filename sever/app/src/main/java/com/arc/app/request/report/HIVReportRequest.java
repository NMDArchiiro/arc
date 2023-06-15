package com.arc.app.request.report;

import com.arc.app.entity.report.HIVReport;
import com.arc.app.request.base.AdminUnitRequest;
import com.arc.app.request.base.HealthOrgRequest;
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
    private Boolean isLock;
    private Date modifyDate;
    private AdminUnitRequest province;
    private AdminUnitRequest district;
    private AdminUnitRequest commune;
    private HealthOrgRequest healthOrg;
    private FileDescriptionRequest file;
    private HRIReportRequest hriReport; // Bao cao 1
    private ATHReportRequest athReport; // Bao cao 2
    private POCReportRequest pocReport; // Bao cao 3
    private MDReportRequest mdReport; // Bao cao 4
    private ARVReportRequest arvReport; // Bao cao 5
    private CIReportRequest ciReport; // Bao cao 6
    private MTCReportRequest mtcReport; // Bao cao 7
    private PREPReportRequest prepReport; // Bao cao 8
    private CReportRequest cReport; // Bao cao 9
    private RPREPReportRequest rprep; // Bao cao 10
    private CIHCReportRequest cihcReport; // Bao cao 11
    private HGReportRequest hgReport; // Bao cao 12
    private SPReportRequest spReport; // Bao cao 13
    private ICReportRequest icReport; // Bao cao 14
    private HIReportRequest hiReport; // Bao cao 15
    private String quarterYear;
    private List<NotificationReport> notifications;
    private Boolean canEdit;
    private Boolean canView;
    private Boolean canConfirm;
    private Boolean canConfirmSame;

    public HIVReportRequest(HIVReport entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.quarter = entity.getQuarter();
            this.year = entity.getYear();
            this.status = entity.getStatus();
            this.note = entity.getNote();
            this.isLock = entity.getIsLock();
            this.modifyDate = entity.getLastModifiedDate();
            if (entity.getAdminUnit() != null && entity.getAdminUnit().getLevel() != null) {
                switch (entity.getAdminUnit().getLevel()) {
                    case HIVConstants.LEVEL_PROVINCE:
                        this.province = new AdminUnitRequest(entity.getAdminUnit(), false);
                        break;
                    case HIVConstants.LEVEL_DISTRICT:
                        this.district = new AdminUnitRequest(entity.getAdminUnit(), false);
                        if (entity.getAdminUnit().getParent() != null) {
                            this.province = new AdminUnitRequest(entity.getAdminUnit().getParent(), false);
                        }
                        break;
                    case HIVConstants.LEVEL_COMMUNE:
                        this.commune = new AdminUnitRequest(entity.getAdminUnit(), false);
                        if (entity.getAdminUnit().getParent() != null) {
                            this.district = new AdminUnitRequest(entity.getAdminUnit().getParent(), false);
                            if (entity.getAdminUnit().getParent().getParent() != null) {
                                this.province = new AdminUnitRequest(entity.getAdminUnit().getParent().getParent(), false);
                            }
                        }
                        break;
                }
            }
            if (entity.getHealthOrg() != null) {
                this.healthOrg = new HealthOrgRequest(entity.getHealthOrg(), false);
            }
            if (entity.getFile() != null) {
                this.file = new FileDescriptionRequest(entity.getFile());
            }
            if (entity.getHriReport() != null) {
                this.hriReport = new HRIReportRequest(entity.getHriReport());
            }
            if (entity.getAthReport() != null) {
                this.athReport = new ATHReportRequest(entity.getAthReport());
            }
            if (entity.getPocReport() != null) {
                this.pocReport = new POCReportRequest(entity.getPocReport());
            }
            if (entity.getMdReport() != null) {
                this.mdReport = new MDReportRequest(entity.getMdReport());
            }
            if (entity.getArvReport() != null) {
                this.arvReport = new ARVReportRequest(entity.getArvReport());
            }
            if (entity.getCiReport() != null) {
                this.ciReport = new CIReportRequest(entity.getCiReport());
            }
            if (entity.getMtcReport() != null) {
                this.mtcReport = new MTCReportRequest(entity.getMtcReport());
            }
            if (entity.getPrepReport() != null) {
                this.prepReport = new PREPReportRequest(entity.getPrepReport());
            }
            if (entity.getCReport() != null) {
                this.cReport = new CReportRequest(entity.getCReport());
            }
            if (entity.getRprepReport() != null) {
                this.rprep = new RPREPReportRequest(entity.getRprepReport());
            }
            if (entity.getCihcReport() != null) {
                this.cihcReport = new CIHCReportRequest(entity.getCihcReport());
            }
            if (entity.getHgReport() != null) {
                this.hgReport = new HGReportRequest(entity.getHgReport());
            }
            if (entity.getSpReport() != null) {
                this.spReport = new SPReportRequest(entity.getSpReport());
            }
            if (entity.getIcReport() != null) {
                this.icReport = new ICReportRequest(entity.getIcReport());
            }
            if (entity.getHiReport() != null) {
                this.hiReport = new HIReportRequest(entity.getHiReport());
            }
        }
    }
}
