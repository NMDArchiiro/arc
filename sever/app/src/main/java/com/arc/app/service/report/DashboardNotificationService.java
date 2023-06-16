package com.arc.app.service.report;

import com.arc.app.request.dashboard.DashboardNotification;
import com.arc.app.request.report.NotificationReport;

import java.util.List;
import java.util.UUID;

/**
 * author: NMDuc
 **/
public interface DashboardNotificationService {
    DashboardNotification getNotificationReport();
    List<NotificationReport> getNotificationReportDistrict(Integer quarter, Integer year, Long adminUnitId);
    List<NotificationReport> getNotificationReportProvince(Integer quarter, Integer year, Long adminUnitId);

}
