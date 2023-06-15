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
@Table(name = "tbl_c_report")
@Getter
@Setter
public class CReport extends BaseEntity {
    @OneToOne(mappedBy = "cReport")
    private HIVReport hivReport;

    @OneToMany(mappedBy = "cReport", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CReportContent> contents;
}
