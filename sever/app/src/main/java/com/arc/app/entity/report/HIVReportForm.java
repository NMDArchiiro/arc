package com.arc.app.entity.report;

import com.arc.app.entity.base.AdminUnit;
import com.arc.app.entity.base.BaseEntity;
import com.arc.app.entity.base.FileDescription;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tbl_hiv_report_form")
@Getter
@Setter
public class HIVReportForm extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "province_id")
    private AdminUnit province;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "district_id")
    private AdminUnit district;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "commune_id")
    private AdminUnit commune;

    @ManyToOne
    @JoinColumn(name = "file_id")
    private FileDescription file;

    @Column(name = "note")
    private String note;

    @Column(name="locked")
    private Boolean locked;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "hri_report_id")
    private HRIReport hriReport; // Bao cao hoat dong can thiep giam tac hai
}
