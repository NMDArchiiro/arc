package com.arc.app.entity.report;

import com.arc.app.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * author: NMDuc
 **/
@Entity
@Table(name = "tbl_hg_report_content")
@Getter
@Setter
public class HGReportContent extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "hg_report_id")
    private HGReport hgReport;

    @ManyToOne
    @JoinColumn(name = "report_content_id")
    private ReportContent content;

    @Column(name = "number_manage")
    private Integer numberManage;// so quan ly

    @Column(name = "number_estimate")
    private Integer numberEstimate;// so uoc tinh

    @Column(name = "estimate_method")
    private String estimateMethod;// pp uoc tinh
}
