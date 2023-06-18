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
    // Tinh tu huyen len
    // Lay theo tinh or don vi truc thuoc huyen
    @Query("select new com.arc.app.request.report.RPREPReportContentRequest(entity.content.id,entity.content.title,entity.content.indexNumber," +
            "entity.content.reportNumber,entity.content.subContent,entity.content.bold,entity.content.italics,entity.content.canWrite," +
            "sum(case when entity.numberNCMT is null then 0 else entity.numberNCMT end)," +
            "sum(case when entity.numberMSM is null then 0 else entity.numberMSM end)," +
            "sum(case when entity.numberPNBD is null then 0 else entity.numberPNBD end)," +
            "sum(case when entity.numberTG is null then 0 else entity.numberTG end)," +
            "sum(case when entity.numberOther is null then 0 else entity.numberOther end)," +
            "sum(case when entity.numberTotal is null then 0 else entity.numberTotal end)) " +
            "from RPREPReportContent entity " +
            "Where (entity.voided is null OR entity.voided = false ) " +
            "and entity.rprepReport.hivReport.quarter is null " +
            "and entity.rprepReport.hivReport.status = 5 " +
            "and entity.rprepReport.hivReport.year = :year " +
            "and ((entity.rprepReport.hivReport.adminUnit.parent.id =:administrativeId and entity.rprepReport.hivReport.healthOrg is  null ) or " +
            "(entity.rprepReport.hivReport.adminUnit.id =:administrativeId and entity.rprepReport.hivReport.healthOrg is not null )) " +
            "group by entity.content.id,entity.content.title,entity.content.indexNumber,entity.content.reportNumber,entity.content.subContent,entity.content.bold,entity.content.italics ")
    List<RPREPReportContentRequest> findTotalYear(Integer year, Long administrativeId);
}
