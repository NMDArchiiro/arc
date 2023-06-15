package com.arc.app.service.report;

import com.arc.app.entity.report.PREPReport;
import com.arc.app.request.report.PREPReportRequest;

/**
 * author: NMDuc
 **/
public interface PREPReportService {
    PREPReportRequest getPREPReport(Long id);
    PREPReport setData(PREPReportRequest request);
}
