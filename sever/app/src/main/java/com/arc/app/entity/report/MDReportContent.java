package com.arc.app.entity.report;

import com.arc.app.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * author: NMDuc
 **/
@Entity
@Table(name = "tbl_md_report_content")
@Getter
@Setter
public class MDReportContent extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "md_report_id")
    private MDReport mdReport;

    @ManyToOne
    @JoinColumn(name = "report_content_id")
    private ReportContent content;

    @Column(name = "number_male_patient")
    private Integer numberMalePatient;

    @Column(name = "number_female_patient")
    private Integer numberFemalePatient;

    @Column(name = "number_total_patient")
    private Integer numberTotalPatient;
}
