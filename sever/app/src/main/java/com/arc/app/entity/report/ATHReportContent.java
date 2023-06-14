package com.arc.app.entity.report;

import com.arc.app.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * author: NMDuc
 **/
@Entity
@Table(name = "tbl_ath_report_content")
@Getter
@Setter
public class ATHReportContent extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "ath_report_id")
    private ATHReport athReport;

    @ManyToOne
    @JoinColumn(name = "report_content_id")
    private ReportContent content;

    @Column(name = "number_advise_male")
    private Integer numberAdviseMale; // So luot duong tinh hiv nam

    @Column(name = "number_advise_female")
    private Integer numberAdviseFemale; // So luot duong hiv nu

    @Column(name = "number_advise_total")
    private Integer numberAdviseTotal; // tong so luot duong tinh hiv

    @Column(name = "number_testing_male")
    private Integer numberTestingMale; // so luot xet nghiem nam

    @Column(name = "number_testing_female")
    private Integer numberTestingFemale; // so luot xet nghiem nu

    @Column(name = "number_testing_total")
    private Integer numberTestingTotal; // tong so luot xet nghiem

    @Column(name = "number_person_advise_male")
    private Integer numberPersonAdviseMale; // so nguoi hiv nam

    @Column(name = "number_person_advise_female")
    private Integer numberPersonAdviseFemale; // so nguoi hiv nu

    @Column(name = "number_person_advise_total")
    private Integer numberPersonAdviseTotal; // tong so nguoi hiv

    @Column(name = "number_person_testing_male")
    private Integer numberPersonTestingMale; // so nguoi xet nghiem nam

    @Column(name = "number_person_testing_female")
    private Integer numberPersonTestingFemale; // so nguoi xet nghiem nu

    @Column(name = "number_person_testing_total")
    private Integer numberPersonTestingTotal; // tong so nguoi xet nghiem
}
