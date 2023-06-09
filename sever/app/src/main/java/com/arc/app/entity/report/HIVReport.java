package com.arc.app.entity.report;

import com.arc.app.entity.base.AdminUnit;
import com.arc.app.entity.base.BaseEntity;
import com.arc.app.entity.base.FileDescription;
import com.arc.app.entity.base.HealthOrg;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tbl_hiv_report")
@Getter
@Setter
public class HIVReport extends BaseEntity {
    @Column(name = "reporter")
    private String reporter;// nguoi lam bao cao

    @Column(name = "status")
    private Integer status;

    @Column(name = "quarter")
    private Integer quarter;

    @Column(name = "year")
    private Integer year;

    @Column(name = "note")
    private String note;

    @Column(name = "is_lock")
    private Boolean isLock;

    @ManyToOne
    @JoinColumn(name = "file_id")
    private FileDescription file;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "health_organization_id", nullable = true)
    private HealthOrg healthOrg;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "administrative_unit_id", nullable = true)
    private AdminUnit adminUnit;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "province_id")
    private AdminUnit province;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "district_id")
    private AdminUnit district;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "commune_id")
    private AdminUnit commune;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "hri_report_id")
    private HRIReport hriReport; // Bao cao hoat dong can thiep giam tac hai

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ath_report_id")
    private ATHReport athReport; // Bao cao tu van xet nghiem hiv

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "poc_report_id")
    private POCReport pocReport; // Bao cao truyen thong phong, chong HIV/AID

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "md_report_id")
    private MDReport mdReport; // Dieu tri nghien cac chat dang methadone

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "arv_report_id")
    private ARVReport arvReport; // Quan ly dieu tri arv

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ci_report_id")
    private CIReport ciReport; // Quan ly dieu tri dong nhiem va lao

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "mtc_report_id")
    private MTCReport mtcReport; // Du phong lay truyen tu me sang con

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "prep_report_id")
    private PREPReport prepReport; // Du phong truoc phoi nhiem (PREP)

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "c_report_id")
    private CReport cReport; // Chuan doan som nhiem HIV tre duoi 18 thang tuoi

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "rprep_report_id")
    private RPREPReport rprepReport; // Duy tri dieu tri du phong truoc phoi nhiem HIV (PREP)

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "cihc_report_id")
    private CIHCReport cihcReport; // Dong nhiem HIV va viem gan C

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "hg_report_id")
    private HGReport hgReport; // So luong, doi tuong nguy co cao

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "sp_report_id")
    private SPReport spReport; // Diem cung cap dich vu

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ic_report_id")
    private ICReport icReport; // Kinh phi trien khai phong, chong HIV/AIDS

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "hi_report_id")
    private HIReport hiReport; // Bao hiem y te
}
