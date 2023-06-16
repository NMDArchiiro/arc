package com.arc.app.entity.base;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tbl_user")
@Getter
@Setter
public class User extends BaseEntity {
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "email")
    private String email;

    @Column(name = "account_type")
    private Integer accountType;// 1:don vi trung uong 2: don vi khu vuc 3: don vi tinh 4: don vi huyen 5: don vi xa

    @Column(name = "active")
    private Boolean active;

    @Column(name = "has_hri_report")
    private Boolean hasHRIReport = false;

    @Column(name = "has_ath_report")
    private Boolean hasATHReport = false;

    @Column(name = "has_poc_report")
    private Boolean hasPOCReport = false;

    @Column(name = "has_md_report")
    private Boolean hasMDReport = false;

    @Column(name = "has_arv_report")
    private Boolean hasARVReport = false;

    @Column(name = "has_ci_report")
    private Boolean hasCIReport = false;

    @Column(name = "has_mtc_report")
    private Boolean hasMTCReport = false;

    @Column(name = "has_prep_report")
    private Boolean hasPREPReport = false;

    @Column(name = "has_c_report")
    private Boolean hasCReport = false;

    @Column(name = "has_rprep_report")
    private Boolean hasRPREPReport = false;

    @Column(name = "has_cihc_report")
    private Boolean hasCIHCReport = false;

    @Column(name = "has_hg_report")
    private Boolean hasHGReport = false;

    @Column(name = "has_sp_report")
    private Boolean hasSPReport = false;

    @Column(name = "has_ic_report")
    private Boolean hasICReport = false;

    @Column(name = "has_hi_report")
    private Boolean hasHIReport = false;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
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

    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinTable(name = "tbl_user_role", joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles = new HashSet<>();
}
