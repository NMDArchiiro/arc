package com.arc.app.repository.report;

import com.arc.app.entity.report.CIHCReportContent;
import com.arc.app.request.report.CIHCReportContentRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * author: NMDuc
 **/
@Repository
public interface CIHCReportContentRepository extends JpaRepository<CIHCReportContent, Long> {
    // Tinh tu huyen len
    // Lay theo tinh or don vi truc thuoc huyen
    @Query("select new com.arc.app.request.report.CIHCReportContentRequest(entity.content.id,entity.content.title,entity.content.indexNumber," +
            "entity.content.reportNumber,entity.content.subContent,entity.content.bold,entity.content.italics,entity.content.canWrite, " +
            "sum(case when entity.numberMaleUnder15 is null then 0 else entity.numberMaleUnder15 end)," +
            "sum(case when entity.numberFemaleUnder15 is null then 0 else entity.numberFemaleUnder15 end)," +
            "sum(case when entity.numberTotalUnder15 is null then 0 else entity.numberTotalUnder15 end)," +
            "sum(case when entity.numberMaleOver15 is null then 0 else entity.numberMaleOver15 end)," +
            "sum(case when entity.numberFemaleOver15 is null then 0 else entity.numberFemaleOver15 end)," +
            "sum(case when entity.numberTotalOver15 is null then 0 else entity.numberTotalOver15 end), " +
            "sum(case when entity.numberTotal is null then 0 else entity.numberTotal end)) " +
            "from CIHCReportContent entity " +
            "Where (entity.voided is null OR entity.voided = false ) " +
            "and entity.cihcReport.hivReport.quarter is null " +
            "and entity.cihcReport.hivReport.status = 5 " +
            "and entity.cihcReport.hivReport.year = :year " +
            "and ((entity.cihcReport.hivReport.adminUnit.parent.id =:administrativeId and entity.cihcReport.hivReport.healthOrg is null )  or " +
            "(entity.cihcReport.hivReport.adminUnit.id =:administrativeId and entity.cihcReport.hivReport.healthOrg is not null )) " +
            "group by entity.content.id,entity.content.title,entity.content.indexNumber,entity.content.reportNumber,entity.content.subContent,entity.content.bold,entity.content.italics ")
    List<CIHCReportContentRequest> findTotalYear(Integer year, Long administrativeId);
}
