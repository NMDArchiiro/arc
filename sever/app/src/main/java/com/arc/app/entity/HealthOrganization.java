package com.arc.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "tbl_health_organization")
@Getter
@Setter
public class HealthOrganization extends BaseEntity{
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "level")
    private Integer level;

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
    private HealthOrganization parent;

    @JsonIgnore
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<HealthOrganization> childrens;
}
