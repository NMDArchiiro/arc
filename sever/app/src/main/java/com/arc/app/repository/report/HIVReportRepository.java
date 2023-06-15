package com.arc.app.repository.report;

import com.arc.app.entity.report.HIVReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * author: NMDuc
 **/
@Repository
public interface HIVReportRepository extends JpaRepository<HIVReport, Long> {
    @Query(value = "Select entity from HIVReport entity " +
            "where (entity.locked is null or entity.locked = false) " +
            "and (entity.quarter = :quarter)" +
            "and (entity.year = :year)" +
            "and (entity.adminUnit.id = :adminUnitId) " +
            "and ((:orgId is null and entity.healthOrg is null) or entity.healthOrg.id = :orgId ) ")
    List<HIVReport> hivReportExistQuarter(Integer quarter, Integer year, Long adminUnitId, Long orgId);

    @Query(value = "Select entity from HIVReport entity " +
            "where (entity.locked is null or entity.locked = false) " +
            "and (entity.quarter is null) " +
            "and (entity.year = :year)" +
            "and (entity.adminUnit.id = :adminUnitId) " +
            "and ((:orgId is null and entity.healthOrg is null) or entity.healthOrg.id = :orgId )")
    List<HIVReport> hivReportExistYear(Integer year, Long adminUnitId,Long orgId);

    @Query(value = "select entity from HIVReport entity  where (entity.locked is null or entity.locked = false) " +
            "and (entity.year = :year) " +
            "and (entity.adminUnit.id = :adminUnitId) " +
            "and ((:quarter is null and entity.quarter is null) or (entity.quarter = :quarter)) " +
            "and ((:orgId is null and entity.healthOrg is null) or (entity.healthOrg.id = :orgId))")
    List<HIVReport> findByYearQuarterAdminUnitHealthOrg(Integer year, Integer quarter, Long adminUnitId, Long healthOrgId);
}
