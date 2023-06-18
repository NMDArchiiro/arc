package com.arc.app.repository.report;

import com.arc.app.entity.report.HGReportContent;
import com.arc.app.request.report.HGReportContentRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * author: NMDuc
 **/
@Repository
public interface HGReportContentRepository extends JpaRepository<HGReportContent, Long> {
    // Tinh tu huyen len
    // Lay theo tinh or don vi truc thuoc huyen
    @Query("select new com.arc.app.request.report.HGReportContentRequest(entity.content.id,entity.content.title,entity.content.indexNumber," +
            "entity.content.reportNumber,entity.content.subContent,entity.content.bold,entity.content.italics,entity.content.canWrite," +
            "sum(case when entity.numberManage is null then 0 else entity.numberManage end)," +
            "sum(case when entity.numberEstimate is null then 0 else entity.numberEstimate end)) " +
            "from HGReportContent entity " +
            "Where (entity.voided is null OR entity.voided = false ) " +
            "and entity.hgReport.hivReport.quarter is null " +
            "and entity.hgReport.hivReport.status = 5 " +
            "and entity.hgReport.hivReport.year = :year " +
            "and ((entity.hgReport.hivReport.adminUnit.parent.id =:administrativeId and entity.hgReport.hivReport.healthOrg is  null ) or " +
            "(entity.hgReport.hivReport.adminUnit.id =:administrativeId and entity.hgReport.hivReport.healthOrg is not null )) " +
            "group by entity.content.id,entity.content.title,entity.content.indexNumber,entity.content.reportNumber,'',entity.content.subContent,entity.content.bold,entity.content.italics ")
    List<HGReportContentRequest> findTotalYear(Integer year, Long administrativeId);
}
