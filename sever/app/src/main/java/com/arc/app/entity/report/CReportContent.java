package com.arc.app.entity.report;

import com.arc.app.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * author: NMDuc
 **/
@Entity
@Table(name = "tbl_c_report_content")
@Getter
@Setter
public class CReportContent extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "c_report_id")
    private CReport cReport;

    @ManyToOne
    @JoinColumn(name = "report_content_id")
    private ReportContent content;

    @Column(name = "number_negative")
    private Integer numberNegative;// so luong am tinh

    @Column(name = "number_positive")
    private Integer numberPositive;// so luong duong tinh

    @Column(name = "number_unknown")
    private Integer numberUnknown;// so luong khong xac dinh

    @Column(name = "total")
    private Integer total;// tong
}
