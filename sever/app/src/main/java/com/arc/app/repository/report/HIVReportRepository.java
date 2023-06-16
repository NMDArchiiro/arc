package com.arc.app.repository.report;

import com.arc.app.entity.report.HIVReport;
import com.arc.app.request.report.HIVReportRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    List<HIVReport> hivReportExistYear(Integer year, Long adminUnitId, Long orgId);

    @Query(value = "select entity from HIVReport entity  where (entity.locked is null or entity.locked is false) " +
            "and (entity.year = :year) " +
            "and (entity.adminUnit.id = :adminUnitId) " +
            "and ((:quarter is null and entity.quarter is null) or (entity.quarter = :quarter)) " +
            "and ((:orgId is null and entity.healthOrg is null) or (entity.healthOrg.id = :orgId))")
    List<HIVReport> findByYearQuarterAdminUnitHealthOrg(Integer year, Integer quarter, Long adminUnitId, Long orgId);

    // Page quarter start
    @Query(value = "select new com.arc.app.request.report.HIVReportRequest(entity) from HIVReport entity " +
            "where (entity.locked is null or entity.locked = false) " +
            "and (entity.quarter is not null) " +
            "and (:quarter is null or entity.quarter = :quarter) " +
            "and (:year is null or entity.year = :year) " +
            "and entity.healthOrg is null " +
            "and entity.adminUnit.parent is null " +
            "and (:provinceId is null or entity.adminUnit.id =:provinceId) " +
            "and (coalesce(:provinceIds,null) is null or entity.adminUnit.id in :provinceIds) ",
            countQuery = "select count(entity.id) from HIVReport entity " +
                    "where (entity.locked is null or entity.locked = false) " +
                    "and (entity.quarter is not null) " +
                    "and (:quarter is null or entity.quarter = :quarter) " +
                    "and (:year is null or entity.year = :year) " +
                    "and entity.healthOrg is null " +
                    "and entity.adminUnit.parent is null " +
                    "and (:provinceId is null or entity.adminUnit.id =:provinceId) " +
                    "and (coalesce(:provinceIds,null) is null or entity.adminUnit.id in :provinceIds) ")
    Page<HIVReportRequest> getPageQuarterProvince(Integer quarter, Integer year, Long provinceId, List<Long> provinceIds, Pageable pageable);

    @Query(value = "select new com.arc.app.request.report.HIVReportRequest(entity) from HIVReport entity " +
            "where (entity.locked is null or entity.locked = false) " +
            "and (entity.quarter is not null) " +
            "and (:quarter is null or entity.quarter = :quarter) " +
            "and (:year is null or entity.year = :year) " +
            "and entity.commune is null " +
            "and ((entity.district is not null and entity.healthOrg is null) or (entity.district is null and entity.healthOrg is not null)) "+
            "and (:orgId is null or entity.healthOrg.id = :orgId )  "+
            "and (:provinceId is null or entity.province.id =:provinceId) " +
            "and (:districtId is null or  entity.district.id =:districtId ) " +
            "and (:orgId is null or entity.healthOrg.id =:orgId)",
            countQuery =  "select count(entity.id) from HIVReport entity " +
                    "where (entity.locked is null or entity.locked = false) " +
                    "and (entity.quarter is not null) " +
                    "and (:quarter is null or entity.quarter = :quarter) " +
                    "and (:year is null or entity.year = :year) " +
                    "and entity.commune is null " +
                    "and ((entity.district is not null and entity.healthOrg is null) or (entity.district is null and entity.healthOrg is not null)) "+
                    "and (:orgId is null or entity.healthOrg.id = :orgId )  "+
                    "and (:provinceId is null or entity.province.id =:provinceId) " +
                    "and (:districtId is null or  entity.district.id =:districtId ) " +
                    "and (:orgId is null or entity.healthOrg.id =:orgId)")
    Page<HIVReportRequest> getPageQuarterUnderProvince(Integer quarter, Integer year, Long provinceId, Long districtId, Long orgId, Pageable pageable);

    @Query(value = "select new com.arc.app.request.report.HIVReportRequest(entity) from HIVReport entity " +
            "where (entity.locked is null or entity.locked = false) " +
            "and (entity.quarter is not null) " +
            "and (:quarter is null or entity.quarter = :quarter) " +
            "and (:year is null or entity.year = :year) " +
            "and entity.healthOrg is null " +
            "and entity.adminUnit.parent.parent is null "+
            "and (:provinceId is null or entity.province.id =:provinceId) " +
            "and (:districtId is null or entity.district.id =:districtId)",
            countQuery =  "select count(entity.id) from HIVReport entity " +
                    "where (entity.locked is null or entity.locked = false) " +
                    "and (entity.quarter is not null) " +
                    "and (:quarter is null or entity.quarter = :quarter) " +
                    "and (:year is null or entity.year = :year) " +
                    "and entity.healthOrg is null " +
                    "and entity.adminUnit.parent.parent is null "+
                    "and (:provinceId is null or entity.province.id =:provinceId) " +
                    "and (:districtId is null or entity.district.id =:districtId)")
    Page<HIVReportRequest> getPageQuarterDistrict(Integer quarter,Integer year,Long provinceId,Long districtId, Pageable pageable);

    @Query(value = "select new com.arc.app.request.report.HIVReportRequest(entity) from HIVReport entity " +
            "where (entity.locked is null or entity.locked = false) " +
            "and (entity.quarter is not null) " +
            "and (:quarter is null or entity.quarter = :quarter) " +
            "and (:year is null or entity.year = :year) " +
            "and entity.district is not null " +
            "and ((entity.commune is not null and entity.healthOrg is null) or (entity.commune is null and entity.healthOrg is not null)) "+
            "and (:orgId is null or entity.healthOrg.id = :orgId )  "+
            "and (:provinceId is null or entity.province.id =:provinceId) " +
            "and (:districtId is null or  entity.district.id =:districtId ) " +
            "and (:communeId is null or  entity.commune.id =:communeId ) " +
            "and (:orgId is null or entity.healthOrg.id =:orgId)",
            countQuery =  "select count(entity.id) from HIVReport entity " +
                    "where (entity.locked is null or entity.locked = false) " +
                    "and (entity.quarter is not null) " +
                    "and (:quarter is null or entity.quarter = :quarter) " +
                    "and (:year is null or entity.year = :year) " +
                    "and entity.district is not null " +
                    "and ((entity.commune is not null and entity.healthOrg is null) or (entity.commune is null and entity.healthOrg is not null)) "+
                    "and (:orgId is null or entity.healthOrg.id = :orgId )  "+
                    "and (:provinceId is null or entity.province.id =:provinceId) " +
                    "and (:districtId is null or  entity.district.id =:districtId ) " +
                    "and (:communeId is null or  entity.commune.id =:communeId ) " +
                    "and (:orgId is null or entity.healthOrg.id =:orgId)")
    Page<HIVReportRequest> getPageQuarterUnderDistrict(Integer quarter, Integer year, Long provinceId, Long districtId,Long communeId, Long orgId, Pageable pageable);

    @Query(value = "select new com.arc.app.request.report.HIVReportRequest(entity) from HIVReport entity " +
            "where (entity.locked is null or entity.locked = false) " +
            "and (entity.quarter is not null) " +
            "and (:quarter is null or entity.quarter = :quarter) " +
            "and (:year is null or entity.year = :year) " +
            "and entity.healthOrg is null " +
            "and entity.adminUnit.parent.parent is not null "+
            "and (:provinceId is null or entity.province.id =:provinceId) " +
            "and (:communeId is null or  entity.commune.id =:communeId ) " +
            "and (:districtId is null or entity.district.id =:districtId)",
            countQuery =  "select count(entity.id) from HIVReport entity " +
                    "where (entity.locked is null or entity.locked = false) " +
                    "and (entity.quarter is not null) " +
                    "and (:quarter is null or entity.quarter = :quarter) " +
                    "and (:year is null or entity.year = :year) " +
                    "and entity.healthOrg is null " +
                    "and entity.adminUnit.parent.parent is not null "+
                    "and (:provinceId is null or entity.province.id =:provinceId) " +
                    "and (:communeId is null or  entity.commune.id =:communeId ) " +
                    "and (:districtId is null or entity.district.id =:districtId)")
    Page<HIVReportRequest> getPageQuarterCommune(Integer quarter,Integer year,Long provinceId,Long districtId,Long communeId, Pageable pageable);

    @Query(value = "select new com.arc.app.request.report.HIVReportRequest(entity) from HIVReport entity " +
            "where (entity.locked is null or entity.locked = false) " +
            "and (entity.quarter is not null) " +
            "and (:quarter is null or entity.quarter = :quarter) " +
            "and (:year is null or entity.year = :year) " +
            "and entity.adminUnit.parent is null "+
            "and entity.healthOrg is not null " +
            "and (:orgId is null or entity.healthOrg.id = :orgId )  "+
            "and (:provinceId is null or entity.adminUnit.id =:provinceId) " ,
            countQuery =  "select count(entity.id) from HIVReport entity " +
                    "where (entity.locked is null or entity.locked = false) " +
                    "and (entity.quarter is not null) " +
                    "and (:quarter is null or entity.quarter = :quarter) " +
                    "and (:year is null or entity.year = :year) " +
                    "and entity.adminUnit.parent is null "+
                    "and entity.healthOrg is not null " +
                    "and (:orgId is null or entity.healthOrg.id = :orgId )  "+
                    "and (:provinceId is null or entity.adminUnit.id =:provinceId)")
    Page<HIVReportRequest> getPageQuarterAffiliateProvince(Integer quarter,Integer year,Long provinceId,Long orgId, Pageable pageable);

    @Query(value = "select new com.arc.app.request.report.HIVReportRequest(entity) from HIVReport entity " +
            "where (entity.locked is null or entity.locked = false) " +
            "and (entity.quarter is not null) " +
            "and (:quarter is null or entity.quarter = :quarter) " +
            "and (:year is null or entity.year = :year) " +
            "and entity.adminUnit.parent.parent is null "+
            "and entity.healthOrg is not null " +
            "and (:orgId is null or entity.healthOrg.id = :orgId )  "+
            "and (:provinceId is null or entity.adminUnit.parent.id =:provinceId) " +
            "and (:districtId is null or entity.adminUnit.id = :districtId ) ",
            countQuery =  "select count(entity.id) from HIVReport entity " +
                    "where (entity.locked is null or entity.locked = false) " +
                    "and (entity.quarter is not null) " +
                    "and (:quarter is null or entity.quarter = :quarter) " +
                    "and (:year is null or entity.year = :year) " +
                    "and entity.adminUnit.parent.parent is null "+
                    "and entity.healthOrg is not null " +
                    "and (:orgId is null or entity.healthOrg.id = :orgId )  "+
                    "and (:provinceId is null or entity.adminUnit.parent.id =:provinceId) " +
                    "and (:districtId is null or entity.adminUnit.id = :districtId ) ")
    Page<HIVReportRequest> getPageQuarterAffiliateDistrict(Integer quarter,Integer year,Long provinceId,Long districtId,Long orgId, Pageable pageable);
    // Page quarter end

    // Page year start
    @Query(value = "select new com.arc.app.request.report.HIVReportRequest(entity) from HIVReport entity " +
            "where (entity.locked is null or entity.locked = false) " +
            "and (entity.quarter is  null) " +
            "and (:year is null or entity.year = :year) " +
            "and entity.healthOrg is null " +
            "and entity.adminUnit.parent is null "+
            "and (:provinceId is null or entity.adminUnit.id =:provinceId) " +
            "and (coalesce(:provinceIds,null) is null or entity.adminUnit.id in :provinceIds) ",
            countQuery =  "select count(entity.id) from HIVReport entity " +
                    "where (entity.locked is null or entity.locked = false) " +
                    "and (entity.quarter is  null) " +
                    "and (:year is null or entity.year = :year) " +
                    "and entity.healthOrg is null " +
                    "and entity.adminUnit.parent is null "+
                    "and (:provinceId is null or entity.adminUnit.id =:provinceId) " +
                    "and (coalesce(:provinceIds,null) is null or entity.adminUnit.id in :provinceIds) ")
    Page<HIVReportRequest> getPageYearProvince(Integer year,Long provinceId,List<Long> provinceIds, Pageable pageable);

    @Query(value = "select new com.arc.app.request.report.HIVReportRequest(entity) from HIVReport entity " +
            "where (entity.locked is null or entity.locked = false) " +
            "and (entity.quarter is null) " +
            "and (:year is null or entity.year = :year) " +
            "and entity.commune is null " +
            "and ((entity.district is not null and entity.healthOrg is null) or (entity.district is null and entity.healthOrg is not null)) "+
            "and (:orgId is null or entity.healthOrg.id = :orgId )  "+
            "and (:provinceId is null or entity.province.id =:provinceId) " +
            "and (:districtId is null or  entity.district.id =:districtId ) " +
            "and (:orgId is null or entity.healthOrg.id =:orgId)",
            countQuery =  "select count(entity.id) from HIVReport entity " +
                    "where (entity.locked is null or entity.locked = false) " +
                    "and (entity.quarter is null) " +
                    "and (:year is null or entity.year = :year) " +
                    "and entity.commune is null " +
                    "and ((entity.district is not null and entity.healthOrg is null) or (entity.district is null and entity.healthOrg is not null)) "+
                    "and (:orgId is null or entity.healthOrg.id = :orgId )  "+
                    "and (:provinceId is null or entity.province.id =:provinceId) " +
                    "and (:districtId is null or  entity.district.id =:districtId ) " +
                    "and (:orgId is null or entity.healthOrg.id =:orgId)")
    Page<HIVReportRequest> getPageYearUnderProvince(Integer year, Long provinceId, Long districtId, Long orgId, Pageable pageable);

    @Query(value = "select new com.arc.app.request.report.HIVReportRequest(entity) from HIVReport entity " +
            "where (entity.locked is null or entity.locked = false) " +
            "and (entity.quarter is  null) " +
            "and (:year is null or entity.year = :year) " +
            "and entity.healthOrg is null " +
            "and entity.adminUnit.parent.parent is null "+
            "and (:provinceId is null or entity.province.id =:provinceId) " +
            "and (:districtId is null or entity.district.id =:districtId)",
            countQuery =  "select count(entity.id) from HIVReport entity " +
                    "where (entity.locked is null or entity.locked = false) " +
                    "and (entity.quarter is  null) " +
                    "and (:year is null or entity.year = :year) " +
                    "and entity.healthOrg is null " +
                    "and entity.adminUnit.parent.parent is null "+
                    "and (:provinceId is null or entity.province.id =:provinceId) " +
                    "and (:districtId is null or entity.district.id =:districtId)")
    Page<HIVReportRequest> getPageYearDistrict(Integer year,Long provinceId,Long districtId, Pageable pageable);

    @Query(value = "select new com.arc.app.request.report.HIVReportRequest(entity) from HIVReport entity " +
            "where (entity.locked is null or entity.locked = false) " +
            "and (entity.quarter is null) " +
            "and (:year is null or entity.year = :year) " +
            "and entity.district is not null " +
            "and ((entity.commune is not null and entity.healthOrg is null) or (entity.commune is null and entity.healthOrg is not null)) "+
            "and (:orgId is null or entity.healthOrg.id = :orgId )  "+
            "and (:provinceId is null or entity.province.id =:provinceId) " +
            "and (:districtId is null or  entity.district.id =:districtId ) " +
            "and (:wardId is null or  entity.commune.id =:wardId ) " +
            "and (:orgId is null or entity.healthOrg.id =:orgId)",
            countQuery =  "select count(entity.id) from HIVReport entity " +
                    "where (entity.locked is null or entity.locked = false) " +
                    "and (entity.quarter is null) " +
                    "and (:year is null or entity.year = :year) " +
                    "and entity.district is not null " +
                    "and ((entity.commune is not null and entity.healthOrg is null) or (entity.commune is null and entity.healthOrg is not null)) "+
                    "and (:orgId is null or entity.healthOrg.id = :orgId )  "+
                    "and (:provinceId is null or entity.province.id =:provinceId) " +
                    "and (:districtId is null or  entity.district.id =:districtId ) " +
                    "and (:wardId is null or  entity.commune.id =:wardId ) " +
                    "and (:orgId is null or entity.healthOrg.id =:orgId)")
    Page<HIVReportRequest> getPageYearUnderDistrict(Integer year, Long provinceId, Long districtId,Long wardId, Long orgId, Pageable pageable);

    @Query(value = "select new com.arc.app.request.report.HIVReportRequest(entity) from HIVReport entity " +
            "where (entity.locked is null or entity.locked = false) " +
            "and (entity.quarter is null) " +
            "and (:year is null or entity.year = :year) " +
            "and entity.adminUnit.parent is null "+
            "and entity.healthOrg is not null " +
            "and (:orgId is null or entity.healthOrg.id = :orgId )  "+
            "and (:provinceId is null or entity.adminUnit.id =:provinceId) ",
            countQuery =  "select count(entity.id) from HIVReport entity " +
                    "where (entity.locked is null or entity.locked = false) " +
                    "and (entity.quarter is null) " +
                    "and (:year is null or entity.year = :year) " +
                    "and entity.adminUnit.parent is null "+
                    "and entity.healthOrg is not null " +
                    "and (:orgId is null or entity.healthOrg.id = :orgId )  "+
                    "and (:provinceId is null or entity.adminUnit.id =:provinceId) ")
    Page<HIVReportRequest> getPageYearAffiliateProvince(Integer year,Long provinceId,Long orgId, Pageable pageable);

    @Query(value = "select new com.arc.app.request.report.HIVReportRequest(entity) from HIVReport entity " +
            "where (entity.locked is null or entity.locked = false) " +
            "and (entity.quarter is null) " +
            "and (:year is null or entity.year = :year) " +
            "and entity.adminUnit.parent.parent is null "+
            "and entity.healthOrg is not null " +
            "and (:orgId is null or entity.healthOrg.id = :orgId )  "+
            "and (:provinceId is null or entity.adminUnit.parent.id =:provinceId) " +
            "and (:districtId is null or entity.adminUnit.id = :districtId ) ",
            countQuery =  "select count(entity.id) from HIVReport entity " +
                    "where (entity.locked is null or entity.locked = false) " +
                    "and (entity.quarter is null) " +
                    "and (:year is null or entity.year = :year) " +
                    "and entity.adminUnit.parent.parent is null "+
                    "and entity.healthOrg is not null " +
                    "and (:orgId is null or entity.healthOrg.id = :orgId )  "+
                    "and (:provinceId is null or entity.adminUnit.parent.id =:provinceId) " +
                    "and (:districtId is null or entity.adminUnit.id = :districtId )")
    Page<HIVReportRequest> getPageYearAffiliateDistrict(Integer year,Long provinceId,Long districtId,Long orgId, Pageable pageable);
    // Page year start

    @Query(value = "select new com.arc.app.request.report.HIVReportRequest(entity) from HIVReport entity " +
            "where (entity.locked is null or entity.locked = false) " +
            "and ((entity.quarter is null and entity.year =:yearSearch) " +
            "   or (entity.quarter is not null and entity.quarter =:quarter and entity.year =:year)) " +
            "and ((entity.adminUnit.id in :ids and entity.healthOrg is null ) or entity.healthOrg.id in :ids)")
    List<HIVReportRequest> findAccountParent(List<Long> ids, Integer year, Integer quarter, Integer yearSearch);

    @Query(value = "select new com.arc.app.request.report.HIVReportRequest(entity) from HIVReport entity " +
            "where (entity.locked is null or entity.locked = false) " +
            "and ((:quarter is null and entity.quarter is null) or (entity.quarter =:quarter)) " +
            "and entity.year =:year " +
            "and ((entity.adminUnit.id in :ids and entity.healthOrg is null ) or entity.healthOrg.id in :ids)")
    List<HIVReportRequest> findAccountParent(List<Long> ids, Integer year, Integer quarter);
}
