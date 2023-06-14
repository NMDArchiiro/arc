package com.arc.app.service.report;

import com.arc.app.request.report.HIVReportRequest;

/**
 * author: NMDuc
 **/
public interface HIVReportService {
    HIVReportRequest getHIVReportForm(Long id); // Tim theo id
    HIVReportRequest saveHIVReport(Long id);
    HIVReportRequest setRole(HIVReportRequest request, Integer... type); // Set role
    HIVReportRequest createQuarterCommune(Long id); // Khoi tao bao cao quy xa

}
