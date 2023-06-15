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
@Table(name = "tbl_cihc_repor")
@Getter
@Setter
public class CIHCReport extends BaseEntity {
    @OneToOne(mappedBy = "cihcReport")
    private HIVReport hivReport;

    @OneToMany(mappedBy = "cihcReport", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CIHCReportContent> contents;
}
