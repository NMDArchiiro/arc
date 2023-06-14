package com.arc.app.service.report;

import com.arc.app.request.report.HRIReportRequest;
import com.arc.app.request.report.POCReportRequest;

/**
 * author: NMDuc
 **/
public interface POCReportService {
    POCReportRequest getPOCReport(Long id);
}
