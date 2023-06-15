package com.arc.app.service.report;

import com.arc.app.entity.report.ARVReport;
import com.arc.app.entity.report.MDReport;
import com.arc.app.request.report.ARVReportRequest;
import com.arc.app.request.report.HRIReportRequest;
import com.arc.app.request.report.MDReportRequest;

/**
 * author: NMDuc
 **/
public interface ARVReportService {
    ARVReportRequest getARVReport(Long id);
    ARVReport setData(ARVReportRequest request);
}
