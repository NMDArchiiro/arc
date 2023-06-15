package com.arc.app.service.report;

import com.arc.app.entity.report.RPREPReport;
import com.arc.app.request.report.RPREPReportRequest;

/**
 * author: NMDuc
 **/
public interface RPREPReportService {
    RPREPReportRequest getRPREPReport(Long id);
    RPREPReport setData(RPREPReportRequest request);
}
