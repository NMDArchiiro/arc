package com.arc.app.repository.report;

import com.arc.app.entity.report.ReportContent;
import com.arc.app.request.report.ReportContentRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * author: NMDuc
 **/
@Repository
public interface ReportContentRepository extends JpaRepository<ReportContent, Long> {
    @Query("Select e From ReportContent e Where e.id = ?1")
    ReportContent getById(UUID id);

    @Query("Select e From ReportContent e Where e.reportNumber = ?1 Order by e.indexNumber")
    List<ReportContent> getByReportNumber(Integer reportNumber);

    @Query("select e from ReportContent e where e.indexNumber = ?1 AND e.reportNumber = ?2")
    List<ReportContent> findByNumber(Integer indexNumber, Integer reportNumber);

    @Query(value = "SELECT new com.arc.app.request.report.ReportContentRequest(e) from ReportContent e " +
            "where (:keyword is null or :keyword = '' or e.title like  concat('%',:keyword,'%') " +
            "       or e.reportNumber = :reportNumber) order by e.reportNumber",
            countQuery = "select count(e.id) from ReportContent e " +
                    "where (:keyword is null or :keyword = '' or e.title like  concat('%',:keyword,'%')  " +
                    "   or e.reportNumber = :reportNumber)")
    Page<ReportContentRequest> paging(String keyword, Integer reportNumber, Pageable pageable);

    @Query(value ="SELECT new com.arc.app.request.report.ReportContentRequest(e) from ReportContent e where " +
            "e.reportNumber = :reportNumber " +
            "and (:id is null or e.id <> :id)" )
    List<ReportContentRequest> getAllContentByReportNumber(Integer reportNumber, UUID id);
}
