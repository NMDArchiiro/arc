package com.arc.app.repository.report;

import com.arc.app.entity.report.ATHReportContent;
import com.arc.app.request.report.ATHReportContentRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * author: NMDuc
 **/
@Repository
public interface ATHReportContentRepository extends JpaRepository<ATHReportContent, Long> {
    // Query tinh tong theo huyen
    // DVTT is null, adminUnit lay parent -> huyen
    // DVTT is not null, adminUnit -> huyen
    @Query("select new com.arc.app.request.report.ATHReportContentRequest(entity.content.id,entity.content.title,entity.content.indexNumber," +
            "entity.content.reportNumber,entity.content.subContent,entity.content.bold,entity.content.italics,entity.content.canWrite, " +
            "sum(case when entity.numberAdviseMale is null then 0 else entity.numberAdviseMale end)," +
            "sum(case when entity.numberAdviseFemale is null then 0 else entity.numberAdviseFemale end)," +
            "sum(case when entity.numberAdviseTotal is null then 0 else entity.numberAdviseTotal end)," +
            "sum(case when entity.numberTestingMale is null then 0 else entity.numberTestingMale end)," +
            "sum(case when entity.numberTestingFemale is null then 0 else entity.numberTestingFemale end), " +
            "sum(case when entity.numberTestingTotal is null then 0 else entity.numberTestingTotal end), " +
            "sum(case when entity.numberPersonAdviseMale is null then 0 else entity.numberPersonAdviseMale end)," +
            "sum(case when entity.numberPersonAdviseFemale is null then 0 else entity.numberPersonAdviseFemale end)," +
            "sum(case when entity.numberPersonAdviseTotal is null then 0 else entity.numberPersonAdviseTotal end)," +
            "sum(case when entity.numberPersonTestingMale is null then 0 else entity.numberPersonTestingMale end)," +
            "sum(case when entity.numberPersonTestingFemale is null then 0 else entity.numberPersonTestingFemale end), " +
            "sum(case when entity.numberPersonTestingTotal is null then 0 else entity.numberPersonTestingTotal end)) " +
            "from ATHReportContent entity " +
            "Where (entity.locked is null OR entity.locked = false ) " +
            "and entity.athReport.hivReport.status = 5 " +
            "and entity.athReport.hivReport.quarter is not null " +
            "and entity.athReport.hivReport.quarter = :quarter " +
            "and entity.athReport.hivReport.year = :year " +
            "and ((entity.athReport.hivReport.adminUnit.parent.id =:administrativeId and entity.athReport.hivReport.healthOrg is  null ) or  " +
            "   (entity.athReport.hivReport.adminUnit.id =:administrativeId and entity.athReport.hivReport.healthOrg is not null )) " +
            "group by entity.content.id,entity.content.title,entity.content.indexNumber,entity.content.reportNumber,entity.content.subContent,entity.content.bold,entity.content.italics ")
    List<ATHReportContentRequest> findTotalQuarterFor(Integer year, Integer quarter, UUID administrativeId);
}
