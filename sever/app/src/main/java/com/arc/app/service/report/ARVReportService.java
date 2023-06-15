package com.arc.app.service.report;

import com.arc.app.request.report.ARVReportRequest;
import com.arc.app.request.report.HRIReportRequest;

/**
 * author: NMDuc
 **/
public interface ARVReportService {
    ARVReportRequest getARVReport(Long id);
}
