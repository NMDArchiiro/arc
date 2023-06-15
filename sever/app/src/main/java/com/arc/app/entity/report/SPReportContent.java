package com.arc.app.entity.report;

import com.arc.app.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * author: NMDuc
 **/
@Entity
@Table(name = "tbl_sp_report_content")
@Getter
@Setter
public class SPReportContent extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "sp_report_id")
    private SPReport spReport;

    @ManyToOne
    @JoinColumn(name = "report_content_id")
    private ReportContent content;

    @Column(name = "amount")
    private Integer amount; // so luong
}
