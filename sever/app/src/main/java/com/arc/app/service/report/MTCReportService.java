package com.arc.app.service.report;

import com.arc.app.entity.report.MTCReport;
import com.arc.app.request.report.MTCReportRequest;

/**
 * author: NMDuc
 **/
public interface MTCReportService {
    MTCReportRequest getMTCReport(Long id);
    MTCReport setData(MTCReportRequest request);
}
