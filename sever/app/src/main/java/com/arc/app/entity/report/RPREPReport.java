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
public class RPREPReport extends BaseEntity {
    @OneToOne(mappedBy = "rprepReport")
    private HIVReport hivReport;

    @OneToMany(mappedBy = "rprepReport", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RPREPReportContent> contents;
}
