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
@Table(name = "tbl_ci_report")
@Getter
@Setter
public class CIReport extends BaseEntity {
    @OneToOne(mappedBy = "ciReport")
    private HIVReport hivReport;

    @OneToMany(mappedBy = "ciReport", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CIReportContent> contents;
}
