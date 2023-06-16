package com.arc.app.service.report.impl;

import com.arc.app.request.dashboard.DashboardNotification;
import com.arc.app.request.report.NotificationReport;
import com.arc.app.service.report.DashboardNotificationService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * author: NMDuc
 **/
@Transactional
@Service
public class DashboardNotificationServiceImpl implements DashboardNotificationService {
    @Override
    public DashboardNotification getNotificationReport() {
        return null;
    }

    @Override
    public List<NotificationReport> getNotificationReportDistrict(Integer quarter, Integer year, Long adminUnitId) {
        return null;
    }

    @Override
    public List<NotificationReport> getNotificationReportProvince(Integer quarter, Integer year, Long adminUnitId) {
        return null;
    }
}
