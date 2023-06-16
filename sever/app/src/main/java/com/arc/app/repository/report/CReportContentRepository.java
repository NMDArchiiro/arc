package com.arc.app.repository.report;

import com.arc.app.entity.report.CReportContent;
import com.arc.app.request.report.CReportContentRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * author: NMDuc
 **/
@Repository
public interface CReportContentRepository extends JpaRepository<CReportContent, Long> {
    // Tinh tu huyen len
    // Lay theo tinh or don vi truc thuoc huyen
    @Query("select new com.arc.app.request.report.CReportContentRequest(entity.content.id,entity.content.title,entity.content.indexNumber," +
            "entity.content.reportNumber,entity.content.subContent,entity.content.bold,entity.content.italics,entity.content.canWrite, " +
            "sum(case when entity.numberNegative is null then 0 else entity.numberNegative end)," +
            "sum(case when entity.numberPositive is null then 0 else entity.numberPositive end)," +
            "sum(case when entity.numberUnknown is null then 0 else entity.numberUnknown end)," +
            "sum(case when entity.total is null then 0 else entity.total end)) " +
            "from CReportContent entity " +
            "Where (entity.locked is null OR entity.locked = false ) " +
            "and entity.cReport.hivReport.quarter is not null " +
            "and entity.cReport.hivReport.status = 5 " +
            "and entity.cReport.hivReport.quarter = :quarter " +
            "and entity.cReport.hivReport.year = :year " +
            "and ((entity.cReport.hivReport.adminUnit.parent.id =:administrativeId and entity.cReport.hivReport.healthOrg is null ) or " +
            "   (entity.cReport.hivReport.adminUnit.id =:administrativeId and " +
            "		entity.cReport.hivReport.healthOrg is not null )) " +
            "group by entity.content.id,entity.content.title,entity.content.indexNumber,entity.content.reportNumber,entity.content.subContent,entity.content.bold,entity.content.italics ")
    List<CReportContentRequest> findTotalQuarterFor(Integer year, Integer quarter, Long administrativeId);
}
