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
@Table(name = "tbl_arv_report")
@Getter
@Setter
public class ARVReport extends BaseEntity {
    @OneToOne(mappedBy = "arvReport")
    private HIVReport hivReport;

    @OneToMany(mappedBy = "arvReport", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ARVReportContent> contents;
}
