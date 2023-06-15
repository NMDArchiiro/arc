package com.arc.app.entity.report;

import com.arc.app.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * author: NMDuc
 **/
@Entity
@Table(name = "tbl_cihc_report_content")
@Getter
@Setter
public class CIHCReportContent extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "cihc_report_id")
    private CIHCReport cihcReport;

    @ManyToOne
    @JoinColumn(name = "report_content_id")
    private ReportContent content;

    @Column(name = "number_male_under_15")
    private Integer numberMaleUnder15; // nam duoi 15

    @Column(name = "number_female_under_15")
    private Integer numberFemaleUnder15;  // nu duoi 15

    @Column(name = "number_total_under_15")
    private Integer numberTotalUnder15; // tong duoi 15

    @Column(name = "number_male_over_15")
    private Integer numberMaleOver15; // nam tren 15

    @Column(name = "number_female_over_15")
    private Integer numberFemaleOver15; // nu tren 15

    @Column(name = "number_total_over_15")
    private Integer numberTotalOver15; // tong tren 15

    @Column(name = "number_total")
    private Integer numberTotal; // tong
}
