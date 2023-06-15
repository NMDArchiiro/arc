package com.arc.app.service.report;

import com.arc.app.entity.report.CIHCReport;
import com.arc.app.entity.report.RPREPReport;
import com.arc.app.request.report.ATHReportRequest;
import com.arc.app.request.report.CIHCReportRequest;
import com.arc.app.request.report.RPREPReportRequest;

/**
 * author: NMDuc
 **/
public interface CIHCReportService {
    CIHCReportRequest getCIHCReport(Long id);
    CIHCReport setData(CIHCReportRequest request);
}
