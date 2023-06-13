package com.arc.app.service.report;

import com.arc.app.entity.report.ReportContent;
import com.arc.app.request.report.ReportContentRequest;
import com.arc.app.response.ResponseList;
import com.arc.app.response.ResponseObject;

/**
 * author: NMDuc
 **/
public interface ReportContentService {
    ResponseObject saveReportContent(ReportContentRequest request);
    ResponseObject getReportContent(Long id);
    ResponseList<ReportContent> getByIndexNumber(Integer indexNumber, Integer reportNumber);
    ResponseList<ReportContent> getAllContentByReportNumber(Integer reportNumber);
}
