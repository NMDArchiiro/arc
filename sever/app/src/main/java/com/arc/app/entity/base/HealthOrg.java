package com.arc.app.entity.base;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tbl_health_org")
@Getter
@Setter
public class HealthOrg extends BaseEntity{
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "level")
    private Integer level; // TYPE_ORG_VAAC,TYPE_ORG_AREA,TYPE_ORG_PROVINCE,TYPE_ORG_DISTRICT,TYPE_ORG_COMMUNE

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "province_id")
    private AdminUnit province;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "district_id")
    private AdminUnit district;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "commune_id")
    private AdminUnit commune;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private HealthOrg parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<HealthOrg> childrens;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "healthOrg",orphanRemoval = true)
    List<AdminUnitHealthOrg> listAdminUnit = new ArrayList<>();
}
