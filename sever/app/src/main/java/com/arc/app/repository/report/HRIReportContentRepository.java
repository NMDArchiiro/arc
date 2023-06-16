package com.arc.app.repository.report;

import com.arc.app.entity.report.HRIReportContent;
import com.arc.app.request.report.HRIReportContentRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * author: NMDuc
 **/
@Repository
public interface HRIReportContentRepository extends JpaRepository<HRIReportContent, Long> {
    // Tinh tu huyen len
    // Lay theo tinh or don vi truc thuoc huyen
    @Query("select new com.arc.app.request.report.HRIReportContentRequest(entity.content.id,entity.content.title,entity.content.indexNumber," +
            "entity.content.reportNumber,entity.content.canWrite, " +
            "sum(case when entity.numberSyringeNeedle is null then 0 else entity.numberSyringeNeedle end)," +
            "sum(case when entity.numberCondom is null then 0 else entity.numberCondom end)," +
            "sum(case when entity.numberLubricant is null then 0 else entity.numberLubricant end)) " +
            "from HRIReportContent entity " +
            "Where (entity.locked is null OR entity.locked is false ) " +
            "and entity.hriReport.hivReport.status = 5 " +
            "and entity.hriReport.hivReport.quarter is not null " +
            "and entity.hriReport.hivReport.quarter = :quarter " +
            "and entity.hriReport.hivReport.year = :year " +
            "and ((entity.hriReport.hivReport.adminUnit.parent.id =:administrativeId and entity.hriReport.hivReport.healthOrg is  null) or " +
            "(entity.hriReport.hivReport.adminUnit.id =:administrativeId and entity.hriReport.hivReport.healthOrg is not null )) " +
            "group by entity.content.id,entity.content.title,entity.content.indexNumber,entity.content.reportNumber ")
    List<HRIReportContentRequest> findTotalQuarterFor(Integer year, Integer quarter, Long administrativeId);
}
