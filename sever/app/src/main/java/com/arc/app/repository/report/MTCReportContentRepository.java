package com.arc.app.repository.report;

import com.arc.app.entity.report.MTCReportContent;
import com.arc.app.request.report.MTCReportContentRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * author: NMDuc
 **/
@Repository
public interface MTCReportContentRepository extends JpaRepository<MTCReportContent, Long> {
    // Tinh tu huyen len
    // Lay theo tinh or don vi truc thuoc huyen
    @Query("select new com.arc.app.request.report.MTCReportContentRequest(entity.content.id,entity.content.title,entity.content.indexNumber," +
            "entity.content.reportNumber,entity.content.subContent,entity.content.bold,entity.content.italics,entity.content.canWrite," +
            "sum(case when entity.amount is null then 0 else entity.amount end))" +
            "from MTCReportContent entity " +
            "Where (entity.locked is null OR entity.locked = false ) " +
            "and entity.mtcReport.hivReport.quarter is not null " +
            "and entity.mtcReport.hivReport.status = 5 " +
            "and entity.mtcReport.hivReport.quarter = :quarter " +
            "and entity.mtcReport.hivReport.year = :year " +
            "and ((entity.mtcReport.hivReport.adminUnit.parent.id =:administrativeId and entity.mtcReport.hivReport.healthOrg is null ) or " +
            "   (entity.mtcReport.hivReport.adminUnit.id =:administrativeId and entity.mtcReport.hivReport.healthOrg is not null )) " +
            "group by entity.content.id,entity.content.title,entity.content.indexNumber,entity.content.reportNumber,entity.content.subContent,entity.content.bold,entity.content.italics ")
    List<MTCReportContentRequest> findTotalQuarterFor(Integer year, Integer quarter, Long administrativeId);
}
