package com.arc.app.repository.base;

import com.arc.app.entity.base.HealthOrg;
import com.arc.app.request.base.HealthOrgRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HealthOrgRepository extends JpaRepository<HealthOrg, Long> {
    @Query(value = "select count(e) from HealthOrg e where (e.locked is null  or e.locked is false) and e.code like :code")
    Long countDuplicate(String code);

    @Query("select new com.arc.app.request.base.HealthOrgRequest(entity, false) From HealthOrg entity" +
            " Where (entity.locked is null OR entity.locked = false) " +
            "and (entity.province.id =:adminUnitId or entity.district.id =:adminUnitId or entity.commune.id =:adminUnitId) " +
            "and (:healOrgId is null OR entity.id = :healOrgId) " +
            "and (:type is null or entity.level = :level)")
    List<HealthOrgRequest> getHealthOrganizationByAdminUnit(Long adminUnitId, Long healOrgId, Integer level);
}
