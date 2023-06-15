package com.arc.app.entity.report;

import com.arc.app.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * author: NMDuc
 **/
@Entity
@Table(name = "tbl_hi_report")
@Getter
@Setter
public class HIReport extends BaseEntity {
    @OneToOne(mappedBy = "hiReport")
    private HIVReport hivReport;

    @OneToMany(mappedBy = "hiReport", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<HIReportContent> contents;
}
