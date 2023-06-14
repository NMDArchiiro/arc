package com.arc.app.entity.report;

import com.arc.app.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tbl_hri_report_content")
@Getter
@Setter
public class HRIReportContent extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "hri_report_id")
    private HRIReport hriReport;

    @ManyToOne
    @JoinColumn(name = "report_content_id")
    private ReportContent content;

    @Column(name = "number_syringe_needle")
    private Integer numberSyringeNeedle;// bom kim tiem

    @Column(name = "number_condom")
    private Integer numberCondom;// bao cao su

    @Column(name = "number_lubricant")
    private Integer numberLubricant;// chat boi tron
}
