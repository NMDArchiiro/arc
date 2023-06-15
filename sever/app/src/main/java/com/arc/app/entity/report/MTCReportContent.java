package com.arc.app.entity.report;

import com.arc.app.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * author: NMDuc
 **/
@Entity
@Table(name = "tbl_mtc_report_content")
@Getter
@Setter
public class MTCReportContent extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "mtc_report_id")
    private MTCReport mtcReport;

    @ManyToOne
    @JoinColumn(name = "report_content_id")
    private ReportContent content;

    @Column(name = "amount")
    private Integer amount; // So luong

}
