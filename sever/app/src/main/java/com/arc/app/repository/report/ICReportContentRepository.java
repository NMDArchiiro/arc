package com.arc.app.repository.report;

import com.arc.app.entity.report.ICReportContent;
import com.arc.app.request.report.ICReportContentRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * author: NMDuc
 **/
@Repository
public interface ICReportContentRepository extends JpaRepository<ICReportContent, Long> {
    // Tinh tu huyen len
    // Lay theo tinh or don vi truc thuoc huyen
    @Query("select new com.arc.app.request.report.ICReportContentRequest(entity.content.id,entity.content.title,entity.content.indexNumber," +
            "entity.content.reportNumber,entity.content.subContent,entity.content.bold,entity.content.italics,entity.content.canWrite," +
            "sum(case when entity.provision is null then 0 else entity.provision end)," +
            "sum(case when entity.treatment is null then 0 else entity.treatment end)," +
            "sum(case when entity.latest is null then 0 else entity.latest end)," +
            "sum(case when entity.reviews is null then 0 else entity.reviews end)," +
            "sum(case when entity.capacityBuilding is null then 0 else entity.capacityBuilding end )) " +
            "from ICReportContent entity " +
            "Where (entity.locked is null OR entity.locked = false ) " +
            "and entity.icReport.hivReport.quarter is null " +
            "and entity.icReport.hivReport.status = 5 " +
            "and entity.icReport.hivReport.year = :year " +
            "and ((entity.icReport.hivReport.adminUnit.parent.id =:administrativeId and entity.icReport.hivReport.healthOrg is  null) " +
            " or (entity.icReport.hivReport.adminUnit.id =:administrativeId and entity.icReport.hivReport.healthOrg is not null )) " +
            "group by entity.content.id,entity.content.title,entity.content.indexNumber,entity.content.reportNumber,entity.content.subContent,entity.content.bold,entity.content.italics ")
    List<ICReportContentRequest> findTotalYear(Integer year, Long administrativeId);
}
