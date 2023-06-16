package com.arc.app.request.dashboard;

import com.arc.app.request.report.NotificationReport;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * author: NMDuc
 **/
@Getter
@Setter
@NoArgsConstructor
public class DashboardNotification {
    private Integer quarterReportQuarter;
    private Integer yearReportQuarter;
    private Integer yearReportYear;
    private List<NotificationReport> notifications;
}
