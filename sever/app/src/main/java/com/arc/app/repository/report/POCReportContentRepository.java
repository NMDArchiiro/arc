package com.arc.app.repository.report;

import com.arc.app.entity.report.POCReportContent;
import com.arc.app.request.report.POCReportContentRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * author: NMDuc
 **/
@Repository
public interface POCReportContentRepository extends JpaRepository<POCReportContent, Long> {
    // Tinh tu huyen len
    // Lay theo tinh or don vi truc thuoc huyen
    @Query("select new com.arc.app.request.report.POCReportContentRequest(entity.content.id,entity.content.title,entity.content.indexNumber," +
            "entity.content.reportNumber,entity.content.subContent,entity.content.bold,entity.content.italics,entity.content.canWrite," +
            "sum(case when entity.amount is null then 0 else entity.amount end))" +
            "from POCReportContent entity " +
            "Where (entity.voided is null OR entity.voided = false ) " +
            "and entity.pocReport.hivReport.quarter is not null " +
            "and entity.pocReport.hivReport.status = 5 " +
            "and entity.pocReport.hivReport.quarter = :quarter " +
            "and entity.pocReport.hivReport.year = :year " +
            "and ((entity.pocReport.hivReport.adminUnit.parent.id =:administrativeId and entity.pocReport.hivReport.healthOrg is null ) or " +
            "   (entity.pocReport.hivReport.adminUnit.id =:administrativeId and " +
            "		entity.pocReport.hivReport.healthOrg is not null )) " +
            "group by entity.content.id,entity.content.title,entity.content.indexNumber,entity.content.reportNumber,entity.content.subContent,entity.content.bold,entity.content.italics ")
    List<POCReportContentRequest> findTotalQuarterFor(Integer year, Integer quarter, Long administrativeId);
}
