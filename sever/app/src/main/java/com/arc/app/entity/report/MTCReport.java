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
@Table(name = "tbl_mtc_report")
@Getter
@Setter
public class MTCReport extends BaseEntity {
    @OneToOne(mappedBy = "mtcReport")
    private HIVReport hivReport;

    @OneToMany(mappedBy = "mtcReport", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MTCReportContent> contents;
}
