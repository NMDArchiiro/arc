package com.arc.app.entity.report;

import com.arc.app.entity.base.AdminUnit;
import com.arc.app.entity.base.BaseEntity;
import com.arc.app.entity.base.FileDescription;
import com.arc.app.entity.base.HealthOrganization;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tbl_hiv_report_form")
@Getter
@Setter
public class HIVReport extends BaseEntity {
    @Column(name = "reporter")
    private String reporter;// nguoi lam bao cao

    @Column(name = "status")
    private Integer status;

    @Column(name = "quarter")
    private Integer quarter;

    @Column(name = "year")
    private Integer year;

    @Column(name = "note")
    private String note;

    @Column(name="locked")
    private Boolean locked;

    @ManyToOne
    @JoinColumn(name = "file_id")
    private FileDescription file;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "health_organization_id", nullable = true)
    private HealthOrganization healthOrg;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "administrative_unit_id", nullable = true)
    private AdminUnit adminUnit;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "hri_report_id")
    private HRIReport hriReport; // Bao cao hoat dong can thiep giam tac hai
}
