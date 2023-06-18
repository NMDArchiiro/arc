package com.arc.app.repository.report;

import com.arc.app.entity.report.CIReportContent;
import com.arc.app.request.report.CIReportContentRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * author: NMDuc
 **/
@Repository
public interface CIReportContentRepository extends JpaRepository<CIReportContent, Long> {
    // Tinh tu huyen len
    // Lay theo tinh or don vi truc thuoc huyen
    @Query("select new com.arc.app.request.report.CIReportContentRequest(entity.content.id,entity.content.title,entity.content.indexNumber," +
            "entity.content.reportNumber,entity.content.subContent,entity.content.bold,entity.content.italics,entity.content.canWrite, " +
            "sum(case when entity.numberMaleUnder15 is null then 0 else entity.numberMaleUnder15 end)," +
            "sum(case when entity.numberFemaleUnder15 is null then 0 else entity.numberFemaleUnder15 end)," +
            "sum(case when entity.numberTotalUnder15 is null then 0 else entity.numberTotalUnder15 end)," +
            "sum(case when entity.numberMaleOver15 is null then 0 else entity.numberMaleOver15 end)," +
            "sum(case when entity.numberFemaleOver15 is null then 0 else entity.numberFemaleOver15 end)," +
            "sum(case when entity.numberTotalOver15 is null then 0 else entity.numberTotalOver15 end), " +
            "sum(case when entity.numberTotal is null then 0 else entity.numberTotal end)) " +
            "from CIReportContent entity " +
            "Where (entity.voided is null OR entity.voided = false ) " +
            "and entity.ciReport.hivReport.quarter is not null " +
            "and entity.ciReport.hivReport.status = 5 " +
            "and entity.ciReport.hivReport.quarter = :quarter " +
            "and entity.ciReport.hivReport.year = :year " +
            "and ((entity.ciReport.hivReport.adminUnit.parent.id =:administrativeId and entity.ciReport.hivReport.healthOrg is null )  or " +
            "   (entity.ciReport.hivReport.adminUnit.id =:administrativeId and entity.ciReport.hivReport.healthOrg is not null )) " +
            "group by entity.content.id,entity.content.title,entity.content.indexNumber,entity.content.reportNumber,entity.content.subContent,entity.content.bold,entity.content.italics ")
    List<CIReportContentRequest> findTotalQuarterFor(Integer year, Integer quarter, Long administrativeId);
}
