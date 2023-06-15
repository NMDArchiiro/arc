package com.arc.app.repository.report;

import com.arc.app.entity.report.RPREPReportContent;
import com.arc.app.request.report.RPREPReportContentRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * author: NMDuc
 **/
@Repository
public interface RPREPReportContentRepository extends JpaRepository<RPREPReportContent, Long> {
    // Query tinh tong theo huyen
    // DVTT is null, adminUnit lay parent -> huyen
    // DVTT is not null, adminUnit -> huyen
    @Query("select new com.arc.app.request.report.RPREPReportContentRequest(entity.content.id,entity.content.title,entity.content.indexNumber," +
            "entity.content.reportNumber,entity.content.subContent,entity.content.bold,entity.content.italics,entity.content.canWrite," +
            "sum(case when entity.numberNCMT is null then 0 else entity.numberNCMT end)," +
            "sum(case when entity.numberMSM is null then 0 else entity.numberMSM end)," +
            "sum(case when entity.numberPNBD is null then 0 else entity.numberPNBD end)," +
            "sum(case when entity.numberTG is null then 0 else entity.numberTG end)," +
            "sum(case when entity.numberOther is null then 0 else entity.numberOther end)," +
            "sum(case when entity.numberTotal is null then 0 else entity.numberTotal end)) " +
            "from RPREPReportContent entity " +
            "Where (entity.locked is null OR entity.locked = false ) " +
            "and entity.rPrepReport.hivReport.quarter is not null " +
            "and entity.rPrepReport.hivReport.status = 5 " +
            "and entity.rPrepReport.hivReport.quarter = :quarter " +
            "and entity.rPrepReport.hivReport.year = :year " +
            "and ((entity.rPrepReport.hivReport.adminUnit.parent.id =:administrativeId and entity.rPrepReport.hivReport.healthOrg is  null ) or " +
            "   (entity.rPrepReport.hivReport.adminUnit.id =:administrativeId and " +
            "		entity.rPrepReport.hivReport.healthOrg is not null )) " +
            "group by entity.content.id,entity.content.title,entity.content.indexNumber,entity.content.reportNumber,entity.content.subContent,entity.content.bold,entity.content.italics ")
    List<RPREPReportContentRequest> findTotalQuarterFor(Integer year, Integer quarter, UUID administrativeId);
}
