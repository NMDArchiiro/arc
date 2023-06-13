package com.arc.app.service.report;

import com.arc.app.request.report.HIVReportRequest;

/**
 * author: NMDuc
 **/
public interface HIVReportService {
    HIVReportRequest getHIVReportForm(Long id);
    HIVReportRequest setRole(HIVReportRequest request, Integer... type);
    HIVReportRequest createQuarterCommune(Long id);

}
