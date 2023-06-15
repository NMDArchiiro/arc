package com.arc.app.entity.report;

import com.arc.app.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * author: NMDuc
 **/
@Entity
@Table(name = "tbl_prep_report_content")
@Getter
@Setter
public class PREPReportContent extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "prep_report_id")
    private PREPReport prepReport;

    @ManyToOne
    @JoinColumn(name = "prep_content_id")
    private ReportContent content;

    @Column(name = "number_ncmt")
    private Integer numberNCMT; // Nghien trich ma tuy

    @Column(name = "number_msm")
    private Integer numberMSM; // Quan he tinh duc dong gioi nam

    @Column(name = "number_pnbd")
    private Integer numberPNBD; // Phu nu ban dam

    @Column(name = "number_tg")
    private Integer numberTG; // Nguoi chuyen doi gioi tinh

    @Column(name = "number_other")
    private Integer numberOther; // Khac

    @Column(name = "number_Total")
    private Integer numberTotal; // Tong
}
