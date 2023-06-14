package com.arc.app.entity.report;

import com.arc.app.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * author: NMDuc
 **/
@Entity
@Table(name = "tbl_poc_report_content")
@Getter
@Setter
public class POCReportContent extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "poc_report_id")
    private POCReport pocReport;

    @ManyToOne
    @JoinColumn(name = "report_content_id")
    private ReportContent content;

    @Column(name = "amount")
    private Integer amount; // so luong

}
