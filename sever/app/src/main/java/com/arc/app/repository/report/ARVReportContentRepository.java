package com.arc.app.repository.report;

import com.arc.app.entity.report.ARVReportContent;
import com.arc.app.request.report.ARVReportContentRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * author: NMDuc
 **/
@Repository
public interface ARVReportContentRepository extends JpaRepository<ARVReportContent, Long> {
    // Query tinh tong theo huyen
    // DVTT is null, adminUnit lay parent -> huyen
    // DVTT is not null, adminUnit -> huyen
    @Query("select new com.arc.app.request.report.ARVReportContentRequest(entity.content.id,entity.content.title,entity.content.indexNumber," +
            "entity.content.reportNumber,entity.content.subContent,entity.content.bold,entity.content.italics,entity.content.canWrite, " +
            "sum(case when entity.numberMaleUnder15 is null then 0 else entity.numberMaleUnder15 end)," +
            "sum(case when entity.numberFemaleUnder15 is null then 0 else entity.numberFemaleUnder15 end)," +
            "sum(case when entity.numberTotalUnder15 is null then 0 else entity.numberTotalUnder15 end)," +
            "sum(case when entity.numberMaleOver15 is null then 0 else entity.numberMaleOver15 end)," +
            "sum(case when entity.numberFemaleOver15 is null then 0 else entity.numberFemaleOver15 end)," +
            "sum(case when entity.numberTotalOver15 is null then 0 else entity.numberTotalOver15 end), " +
            "sum(case when entity.numberTotal is null then 0 else entity.numberTotal end)) " +
            "from ARVReportContent entity " +
            "Where (entity.locked is null OR entity.locked = false ) " +
            "and entity.arvReport.hivReport.quarter is not null " +
            "and entity.arvReport.hivReport.status = 5 " +
            "and entity.arvReport.hivReport.quarter = :quarter " +
            "and entity.arvReport.hivReport.year = :year " +
            "and ((entity.arvReport.hivReport.adminUnit.parent.id =:administrativeId and entity.arvReport.hivReport.healthOrg is null )  or " +
            "   (entity.arvReport.hivReport.adminUnit.id =:administrativeId and " +
            "		entity.arvReport.hivReport.healthOrg is not null )) " +
            "group by entity.content.id,entity.content.title,entity.content.indexNumber,entity.content.reportNumber,entity.content.subContent,entity.content.bold,entity.content.italics ")
    List<ARVReportContentRequest> findTotalQuarterFor(Integer year, Integer quarter, UUID administrativeId);
}
