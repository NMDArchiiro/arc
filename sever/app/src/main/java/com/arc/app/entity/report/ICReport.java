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
@Table(name = "tbl_ic_report")
@Getter
@Setter
public class ICReport extends BaseEntity {
    @OneToOne(mappedBy = "icReport")
    private HIVReport hivReport;

    @OneToMany(mappedBy = "icReport", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ICReportContent> contents;
}
