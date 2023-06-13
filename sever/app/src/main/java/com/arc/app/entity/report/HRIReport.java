package com.arc.app.entity.report;

import com.arc.app.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "tbl_hri_report")
@Getter
@Setter
public class HRIReport extends BaseEntity {
    @OneToOne(mappedBy = "hriReport")
    private HIVReport hivReport;

    @OneToMany(mappedBy = "hriReport", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<HRIReportContent> contents;
}
