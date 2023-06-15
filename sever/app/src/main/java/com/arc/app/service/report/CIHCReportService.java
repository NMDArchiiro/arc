package com.arc.app.service.report;

import com.arc.app.request.report.ATHReportRequest;
import com.arc.app.request.report.CIHCReportRequest;

/**
 * author: NMDuc
 **/
public interface CIHCReportService {
    CIHCReportRequest getCIHCReport(Long id);
}
