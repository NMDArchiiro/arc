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
@Table(name = "tbl_md_report")
@Getter
@Setter
public class MDReport extends BaseEntity {
    @OneToOne(mappedBy = "mdReport")
    private HIVReport hivReport;

    @OneToMany(mappedBy = "mdReport", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MDReportContent> contents;
}
