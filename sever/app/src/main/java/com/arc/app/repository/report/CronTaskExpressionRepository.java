package com.arc.app.repository.report;

import com.arc.app.entity.report.CronTaskExpression;
import com.arc.app.request.report.CronTaskExpressionRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * author: NMDuc
 **/
@Repository
public interface CronTaskExpressionRepository extends JpaRepository<CronTaskExpression, Long> {
    @Query(value = "Select count(entity.id) from CronTaskExpression entity " +
            "where entity.type =:type " +
            "and ((:quarter is null and entity.quarter is null) or entity.quarter =:quarter) " +
            "and (((:hasQuarter is null or :hasQuarter = false) and (entity.hasQuarter is null or entity.hasQuarter = false)) " +
            "or (:hasQuarter = true and entity.hasQuarter = true ))")
    Long checkExist(Boolean hasQuarter, Integer type, Integer quarter);

    @Query(value = "Select new com.arc.app.request.report.CronTaskExpressionRequest(entity) from CronTaskExpression  entity " +
            "where (:keyword is null or entity.description like concat('%',:keyword,'%') ) " +
            "and (:type is null or entity.type =:type) " +
            "order by entity.type",
            countQuery =  "Select count(entity.id) from CronTaskExpression  entity " +
                    "where (:keyword is null or entity.description like concat('%',:keyword,'%') ) " +
                    "and (:type is null or entity.type =:type)")
    Page<CronTaskExpressionRequest> getPage(String keyword, Integer type, Pageable pageable);

    @Query(value = "Select new com.arc.app.request.report.CronTaskExpressionRequest(entity) from CronTaskExpression entity " +
            "where entity.type =:type " +
            "and ((:quarter is null and entity.quarter is null) or entity.quarter =:quarter) " +
            "and (((:hasQuarter is null or :hasQuarter = false) and (entity.hasQuarter is null or entity.hasQuarter = false)) " +
            "or (:hasQuarter = true and entity.hasQuarter = true ))")
    List<CronTaskExpressionRequest> getListCronTaskExpression(Boolean hasQuarter, Integer type, Integer quarter);
}
