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
@Table(name = "tbl_rprep_report")
@Getter
@Setter
public class SPReport extends BaseEntity {
    @OneToOne(mappedBy = "spReport")
    private HIVReport hivReport;

    @OneToMany(mappedBy = "spReport", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SPReportContent> contents;
}
