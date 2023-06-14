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
@Table(name = "tbl_ath_report")
@Getter
@Setter
public class ATHReport extends BaseEntity {
    @OneToOne(mappedBy = "athReport")
    private HIVReport hivReport;

    @OneToMany(mappedBy = "athReport", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ATHReportContent> contents;
}
