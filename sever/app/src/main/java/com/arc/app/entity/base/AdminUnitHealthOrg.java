package com.arc.app.entity.base;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * author: NMDuc
 **/
@Entity
@Table(name = "tbl_admin_unit_health_org")
@Getter
@Setter
public class AdminUnitHealthOrg extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "health_org_id")
    private HealthOrg healthOrg;

    @ManyToOne
    @JoinColumn(name = "administrative_unit_id")
    private AdminUnit adminUnit;
}
