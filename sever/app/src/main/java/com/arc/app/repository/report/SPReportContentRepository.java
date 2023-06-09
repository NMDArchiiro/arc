package com.arc.app.repository.report;

import com.arc.app.entity.report.SPReportContent;
import com.arc.app.request.report.SPReportContentRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * author: NMDuc
 **/
@Repository
public interface SPReportContentRepository extends JpaRepository<SPReportContent, Long> {
    // Tinh tu huyen len
    // Lay theo tinh or don vi truc thuoc huyen
    @Query("select new com.arc.app.request.report.SPReportContentRequest(entity.content.id,entity.content.title,entity.content.indexNumber," +
            "entity.content.reportNumber,entity.content.subContent,entity.content.bold,entity.content.italics,entity.content.canWrite," +
            "sum(case when entity.amount is null then 0 else entity.amount end )) " +
            "from SPReportContent entity " +
            "Where (entity.voided is null OR entity.voided = false ) " +
            "and entity.spReport.hivReport.quarter is null " +
            "and entity.spReport.hivReport.status = 5 " +
            "and entity.spReport.hivReport.year = :year " +
            "and ((entity.spReport.hivReport.adminUnit.parent.id =:administrativeId and entity.spReport.hivReport.healthOrg is  null ) or " +
            "(entity.spReport.hivReport.adminUnit.id =:administrativeId and entity.spReport.hivReport.healthOrg is not null )) " +
            "group by entity.content.id,entity.content.title,entity.content.indexNumber,entity.content.reportNumber,entity.content.subContent,entity.content.bold,entity.content.italics ")
    List<SPReportContentRequest> findTotalYear(Integer year, Long administrativeId);
}
