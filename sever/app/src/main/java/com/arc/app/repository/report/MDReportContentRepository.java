package com.arc.app.repository.report;

import com.arc.app.entity.report.MDReportContent;
import com.arc.app.request.report.MDReportContentRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * author: NMDuc
 **/
@Repository
public interface MDReportContentRepository extends JpaRepository<MDReportContent, Long> {
    // Query tinh tong theo huyen
    // DVTT is null, adminUnit lay parent -> huyen
    // DVTT is not null, adminUnit -> huyen
    @Query("select new com.arc.app.request.report.MDReportContentRequest(entity.content.id,entity.content.title,entity.content.indexNumber," +
            "entity.content.reportNumber,entity.content.subContent,entity.content.bold,entity.content.italics,entity.content.canWrite," +
            "sum(case when entity.numberMalePatient is null then 0 else entity.numberMalePatient end)," +
            "sum(case when entity.numberFemalePatient is null then 0 else entity.numberFemalePatient end)," +
            "sum(case when entity.numberTotalPatient is null then 0 else entity.numberTotalPatient end)) " +
            "from MDReportContent entity " +
            "Where (entity.locked is null OR entity.locked = false ) " +
            "and entity.mdReport.hivReport.quarter is not null " +
            "and entity.mdReport.hivReport.status = 5 " +
            "and entity.mdReport.hivReport.quarter = :quarter " +
            "and entity.mdReport.hivReport.year = :year " +
            "and ((entity.mdReport.hivReport.adminUnit.parent.id =:administrativeId and entity.mdReport.hivReport.healthOrg is null)  or " +
            "   (entity.mdReport.hivReport.adminUnit.id =:administrativeId and entity.mdReport.hivReport.healthOrg is not null )) " +
            "group by entity.content.id,entity.content.title,entity.content.indexNumber,entity.content.reportNumber,entity.content.subContent,entity.content.bold,entity.content.italics ")
    List<MDReportContentRequest> findTotalQuarterFor(Integer year, Integer quarter, UUID administrativeId);
}
