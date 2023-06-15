package com.arc.app.entity.report;

import com.arc.app.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * author: NMDuc
 **/
@Entity
@Table(name = "tbl_ic_report_content")
@Getter
@Setter
public class ICReportContent extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "ic_report_id")
    private ICReport icReport;

    @ManyToOne
    @JoinColumn(name = "report_content_id")
    private ReportContent content;

    @Column(name = "provision")
    private Integer provision;// chi phi du phong

    @Column(name = "treatment")
    private Integer treatment;// chi phi dieu tri

    @Column(name = "latest")
    private Integer latest;// chi phi xet nghiem

    @Column(name = "reviews")
    private Integer reviews;// chi phi theo doi danh gia

    @Column(name = "capacity_building")
    private Integer capacityBuilding;// chi phi nang cao nang luc
}
