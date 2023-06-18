package com.arc.app.repository.report;

import com.arc.app.entity.report.PREPReportContent;
import com.arc.app.request.report.PREPReportContentRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * author: NMDuc
 **/
@Repository
public interface PREPReportContentRepository extends JpaRepository<PREPReportContent, Long> {
    // Tinh tu huyen len
    // Lay theo tinh or don vi truc thuoc huyen
    @Query("select new com.arc.app.request.report.PREPReportContentRequest(entity.content.id,entity.content.title,entity.content.indexNumber," +
            "entity.content.reportNumber,entity.content.subContent,entity.content.bold,entity.content.italics,entity.content.canWrite," +
            "sum(case when entity.numberNCMT is null then 0 else entity.numberNCMT end)," +
            "sum(case when entity.numberMSM is null then 0 else entity.numberMSM end)," +
            "sum(case when entity.numberPNBD is null then 0 else entity.numberPNBD end)," +
            "sum(case when entity.numberTG is null then 0 else entity.numberTG end)," +
            "sum(case when entity.numberOther is null then 0 else entity.numberOther end)," +
            "sum(case when entity.numberTotal is null then 0 else entity.numberTotal end)) " +
            "from PREPReportContent entity " +
            "Where (entity.voided is null OR entity.voided = false ) " +
            "and entity.prepReport.hivReport.quarter is not null " +
            "and entity.prepReport.hivReport.status = 5 " +
            "and entity.prepReport.hivReport.quarter = :quarter " +
            "and entity.prepReport.hivReport.year = :year " +
            "and ((entity.prepReport.hivReport.adminUnit.parent.id =:administrativeId and entity.prepReport.hivReport.healthOrg is  null ) or " +
            "   (entity.prepReport.hivReport.adminUnit.id =:administrativeId and " +
            "		entity.prepReport.hivReport.healthOrg is not null )) " +
            "group by entity.content.id,entity.content.title,entity.content.indexNumber,entity.content.reportNumber,entity.content.subContent,entity.content.bold,entity.content.italics ")
    List<PREPReportContentRequest> findTotalQuarterFor(Integer year, Integer quarter, Long administrativeId);
}
