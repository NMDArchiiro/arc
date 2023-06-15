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
@Table(name = "tbl_prep_report")
@Getter
@Setter
public class PREPReport extends BaseEntity {
    @OneToOne(mappedBy = "prepReport")
    private HIVReport hivReport;

    @OneToMany(mappedBy = "prepReport", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PREPReportContent> contents;
}
