package com.arc.app.service.report.impl;

import com.arc.app.entity.base.AdminUnit;
import com.arc.app.repository.base.AdminUnitRepository;
import com.arc.app.repository.base.HealthOrgRepository;
import com.arc.app.repository.report.CronTaskExpressionRepository;
import com.arc.app.repository.report.HIVReportRepository;
import com.arc.app.request.base.AdminUnitRequest;
import com.arc.app.request.base.HealthOrgRequest;
import com.arc.app.request.base.UserRequest;
import com.arc.app.request.dashboard.DashboardNotification;
import com.arc.app.request.dto.AdminUnitDto;
import com.arc.app.request.report.CronTaskExpressionRequest;
import com.arc.app.request.report.HIVReportRequest;
import com.arc.app.request.report.NotificationReport;
import com.arc.app.service.base.AdminUnitService;
import com.arc.app.service.base.UserSecurityService;
import com.arc.app.service.report.DashboardNotificationService;
import com.arc.app.utils.enums.AccountTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * author: NMDuc
 **/
@Transactional
@Service
public class DashboardNotificationServiceImpl implements DashboardNotificationService {
    @Resource
    private CronTaskExpressionRepository cronTaskExpressionRepository;
    @Resource
    private UserSecurityService userSecurityService;
    @Resource
    private AdminUnitRepository adminUnitRepository;
    @Resource
    private HealthOrgRepository healthOrgRepository;
    @Resource
    private HIVReportRepository hivReportRepository;
    @Resource
    private AdminUnitService adminUnitService;

    private static Logger LOG = LoggerFactory.getLogger(DashboardNotificationServiceImpl.class);

    @Override
    public DashboardNotification getNotificationReport() {
        DashboardNotification dashboardNotification = new DashboardNotification();
        UserRequest currentUser = userSecurityService.getCurrentUser();
        Integer accountType = userSecurityService.getAccountType();
        if (currentUser != null && accountType != null && !accountType.equals(AccountTypeEnum.COMMUNE.getKey()) 
                && !accountType.equals(AccountTypeEnum.UNDER_PROVINCE.getKey()) && !accountType.equals(AccountTypeEnum.UNDER_DISTRICT.getKey())) {
            if (accountType.equals(AccountTypeEnum.DISTRICT.getKey())) {
                HashMap<String, NotificationReport> rs = new HashMap<>();
                List<Long> ids = new ArrayList<>();
                List<Object> listObjects = getCurrent();
                if (!CollectionUtils.isEmpty(listObjects)) {
                    for (Object ob : listObjects) {
                        if (ob instanceof AdminUnitDto) {
                            ids.add(((AdminUnitDto) ob).getId());
                            if (!rs.containsKey(((AdminUnitDto) ob).getId().toString())) {
                                rs.put(((AdminUnitDto) ob).getId().toString(), new NotificationReport(((AdminUnitDto) ob).getId(), ((AdminUnitDto) ob).getName(), "Báo cáo quý"));
                            }
                        }
                        if (ob instanceof HealthOrgRequest) {
                            ids.add(((HealthOrgRequest) ob).getId());
                            if (!rs.containsKey(((HealthOrgRequest) ob).getId().toString() + "-1")) {
                                rs.put(((HealthOrgRequest) ob).getId().toString() + "-" + 1, new NotificationReport(((HealthOrgRequest) ob).getId(), ((HealthOrgRequest) ob).getName(), "Báo cáo quý"));
                            }
                            if (!rs.containsKey(((HealthOrgRequest) ob).getId().toString() + "-0")) {
                                rs.put(((HealthOrgRequest) ob).getId().toString() + "-" + 0, new NotificationReport(((HealthOrgRequest) ob).getId(), ((HealthOrgRequest) ob).getName(), "Báo cáo năm"));
                            }
                        }
                    }
                    dashboardNotification = getTimeDashboard(AccountTypeEnum.DISTRICT.getKey());
                    List<HIVReportRequest> hivReportList = hivReportRepository.findAccountParent(ids, dashboardNotification.getYearReportQuarter(),
                            dashboardNotification.getQuarterReportQuarter(), dashboardNotification.getYearReportYear());
                    if (!CollectionUtils.isEmpty(hivReportList)) {
                        for (HIVReportRequest hivReport : hivReportList) {
                            if (hivReport.getCommune() != null && hivReport.getCommune().getId() != null) {
                                if (rs.containsKey(hivReport.getCommune().getId().toString())) {
                                    NotificationReport dto = rs.get(hivReport.getCommune().getId().toString());
                                    dto.setYear(hivReport.getYear());
                                    dto.setQuarter(hivReport.getQuarter());
                                    dto.setType("Báo cáo quý");
                                    dto.setReporter(hivReport.getReporter());
                                    dto.setNote((hivReport.getStatus() == null || hivReport.getStatus() != 5) ? "Chưa gửi báo cáo" : "Đã gửi báo cáo");
                                    rs.put(hivReport.getCommune().getId().toString(), dto);
                                }
                            }
                            if (hivReport.getHealthOrg() != null && hivReport.getHealthOrg().getId() != null) {
                                if (rs.containsKey(hivReport.getHealthOrg().getId().toString() + "-1") && hivReport.getQuarter() != null) {
                                    NotificationReport dto = rs.get(hivReport.getHealthOrg().getId().toString() + "-1");
                                    dto.setYear(hivReport.getYear());
                                    dto.setQuarter(hivReport.getQuarter());
                                    dto.setType("Báo cáo quý");
                                    dto.setReporter(hivReport.getReporter());
                                    dto.setNote((hivReport.getStatus() == null || hivReport.getStatus() != 5) ? "Chưa gửi báo cáo" : "Đã gửi báo cáo");
                                    rs.put(hivReport.getHealthOrg().getId().toString() + "-1", dto);
                                }
                                if (rs.containsKey(hivReport.getHealthOrg().getId().toString() + "-0") && hivReport.getQuarter() == null) {
                                    NotificationReport dto = rs.get(hivReport.getHealthOrg().getId().toString() + "-0");
                                    dto.setYear(hivReport.getYear());
                                    dto.setQuarter(hivReport.getQuarter());
                                    dto.setType("Báo cáo năm");
                                    dto.setReporter(hivReport.getReporter());
                                    dto.setNote((hivReport.getStatus() == null || hivReport.getStatus() != 5) ? "Chưa gửi báo cáo" : "Đã gửi báo cáo");
                                    rs.put(hivReport.getHealthOrg().getId().toString() + "-0", dto);
                                }
                            }
                        }
                    }
                    dashboardNotification.setNotifications(new ArrayList<>(rs.values()));
                }
                return dashboardNotification;
            }
            if (accountType.equals(AccountTypeEnum.PROVINCE.getKey())) {
                HashMap<String, NotificationReport> rs = new HashMap<>();
                List<Long> ids = new ArrayList<>();
                List<Object> listObjects = getCurrent();
                if (!CollectionUtils.isEmpty(listObjects)) {
                    for (Object ob : listObjects) {
                        if (ob instanceof AdminUnitDto) {
                            ids.add(((AdminUnitDto) ob).getId());
                            if (!rs.containsKey(((AdminUnitDto) ob).getId().toString() + "-1")) {
                                rs.put(((AdminUnitDto) ob).getId().toString() + "-1", new NotificationReport(((AdminUnitDto) ob).getId(), ((AdminUnitDto) ob).getName(), "Báo cáo quý"));
                            }
                            if (!rs.containsKey(((AdminUnitDto) ob).getId().toString() + "-0")) {
                                rs.put(((AdminUnitDto) ob).getId().toString() + "-0", new NotificationReport(((AdminUnitDto) ob).getId(), ((AdminUnitDto) ob).getName(), "Báo cáo năm"));
                            }
                        }
                        if (ob instanceof HealthOrgRequest) {
                            ids.add(((HealthOrgRequest) ob).getId());
                            if (!rs.containsKey(((HealthOrgRequest) ob).getId().toString() + "-1")) {
                                rs.put(((HealthOrgRequest) ob).getId().toString() + "-" + 1, new NotificationReport(((HealthOrgRequest) ob).getId(), ((HealthOrgRequest) ob).getName(), "Báo cáo quý"));
                            }
                            if (!rs.containsKey(((HealthOrgRequest) ob).getId().toString() + "-0")) {
                                rs.put(((HealthOrgRequest) ob).getId().toString() + "-" + 0, new NotificationReport(((HealthOrgRequest) ob).getId(), ((HealthOrgRequest) ob).getName(), "Báo cáo năm"));
                            }
                        }
                    }
                    dashboardNotification = getTimeDashboard(AccountTypeEnum.PROVINCE.getKey());
                    List<HIVReportRequest> hivReportList = hivReportRepository.findAccountParent(ids, dashboardNotification.getYearReportQuarter(),
                            dashboardNotification.getQuarterReportQuarter(), dashboardNotification.getYearReportYear());
                    if (!CollectionUtils.isEmpty(hivReportList)) {
                        for (HIVReportRequest hivReport : hivReportList) {
                            if (hivReport.getDistrict() != null && hivReport.getDistrict().getId() != null) {
                                if (rs.containsKey(hivReport.getDistrict().getId().toString() + "-1") && hivReport.getQuarter() != null) {
                                    NotificationReport dto = rs.get(hivReport.getDistrict().getId().toString() + "-1");
                                    dto.setYear(hivReport.getYear());
                                    dto.setQuarter(hivReport.getQuarter());
                                    dto.setType("Báo cáo quý");
                                    dto.setReporter(hivReport.getReporter());
                                    dto.setNote((hivReport.getStatus() == null || hivReport.getStatus() != 5) ? "Chưa gửi báo cáo" : "Đã gửi báo cáo");
                                    rs.put(hivReport.getDistrict().getId().toString() + "-1", dto);
                                }
                                if (rs.containsKey(hivReport.getDistrict().getId().toString() + "-0") && hivReport.getQuarter() == null) {
                                    NotificationReport dto = rs.get(hivReport.getDistrict().getId().toString() + "-0");
                                    dto.setYear(hivReport.getYear());
                                    dto.setQuarter(hivReport.getQuarter());
                                    dto.setType("Báo cáo năm");
                                    dto.setReporter(hivReport.getReporter());
                                    dto.setNote((hivReport.getStatus() == null || hivReport.getStatus() != 5) ? "Chưa gửi báo cáo" : "Đã gửi báo cáo");
                                    rs.put(hivReport.getDistrict().getId().toString() + "-0", dto);
                                }
                            }
                            if (hivReport.getHealthOrg() != null && hivReport.getHealthOrg().getId() != null) {
                                if (rs.containsKey(hivReport.getHealthOrg().getId().toString() + "-1") && hivReport.getQuarter() != null) {
                                    NotificationReport dto = rs.get(hivReport.getHealthOrg().getId().toString() + "-1");
                                    dto.setYear(hivReport.getYear());
                                    dto.setQuarter(hivReport.getQuarter());
                                    dto.setType("Báo cáo quý");
                                    dto.setReporter(hivReport.getReporter());
                                    dto.setNote((hivReport.getStatus() == null || hivReport.getStatus() != 5) ? "Chưa gửi báo cáo" : "Đã gửi báo cáo");
                                    rs.put(hivReport.getHealthOrg().getId().toString() + "-1", dto);
                                }
                                if (rs.containsKey(hivReport.getHealthOrg().getId().toString() + "-0") && hivReport.getQuarter() == null) {
                                    NotificationReport dto = rs.get(hivReport.getHealthOrg().getId().toString() + "-0");
                                    dto.setYear(hivReport.getYear());
                                    dto.setQuarter(hivReport.getQuarter());
                                    dto.setType("Báo cáo năm");
                                    dto.setReporter(hivReport.getReporter());
                                    dto.setNote((hivReport.getStatus() == null || hivReport.getStatus() != 5) ? "Chưa gửi báo cáo" : "Đã gửi báo cáo");
                                    rs.put(hivReport.getHealthOrg().getId().toString() + "-0", dto);
                                }
                            }
                        }
                    }
                    dashboardNotification.setNotifications(new ArrayList<>(rs.values()));
                }
                return dashboardNotification;
            }
            if (accountType.equals(AccountTypeEnum.AREA.getKey()) || accountType.equals(AccountTypeEnum.VAAC.getKey())) {
                HashMap<String, NotificationReport> rs = new HashMap<>();
                List<Long> ids = new ArrayList<>();
                List<Object> listObjects = getCurrent();
                if (!CollectionUtils.isEmpty(listObjects)) {
                    for (Object ob : listObjects) {
                        if (ob instanceof AdminUnitDto) {
                            ids.add(((AdminUnitDto) ob).getId());
                            if (!rs.containsKey(((AdminUnitDto) ob).getId().toString() + "-1")) {
                                rs.put(((AdminUnitDto) ob).getId().toString() + "-1", new NotificationReport(((AdminUnitDto) ob).getId(), ((AdminUnitDto) ob).getName(), "Báo cáo quý"));
                            }
                            if (!rs.containsKey(((AdminUnitDto) ob).getId().toString() + "-0")) {
                                rs.put(((AdminUnitDto) ob).getId().toString() + "-0", new NotificationReport(((AdminUnitDto) ob).getId(), ((AdminUnitDto) ob).getName(), "Báo cáo năm"));
                            }
                        }
                    }
                    dashboardNotification = getTimeDashboard(AccountTypeEnum.PROVINCE.getKey());
                    List<HIVReportRequest> hivReportList = hivReportRepository.findAccountParent(ids, dashboardNotification.getYearReportQuarter(),
                            dashboardNotification.getQuarterReportQuarter(), dashboardNotification.getYearReportYear());
                    if (!CollectionUtils.isEmpty(hivReportList)) {
                        for (HIVReportRequest hivReport : hivReportList) {
                            if (hivReport.getProvince() != null && hivReport.getProvince().getId() != null) {
                                if (rs.containsKey(hivReport.getProvince().getId().toString() + "-1") && hivReport.getQuarter() != null) {
                                    NotificationReport dto = rs.get(hivReport.getProvince().getId().toString() + "-1");
                                    dto.setYear(hivReport.getYear());
                                    dto.setQuarter(hivReport.getQuarter());
                                    dto.setType("Báo cáo quý");
                                    dto.setReporter(hivReport.getReporter());
                                    dto.setNote((hivReport.getStatus() == null || hivReport.getStatus() != 5) ? "Chưa gửi báo cáo" : "Đã gửi báo cáo");
                                    rs.put(hivReport.getProvince().getId().toString() + "-1", dto);
                                }
                                if (rs.containsKey(hivReport.getProvince().getId().toString() + "-0") && hivReport.getQuarter() == null) {
                                    NotificationReport dto = rs.get(hivReport.getProvince().getId().toString() + "-0");
                                    dto.setYear(hivReport.getYear());
                                    dto.setQuarter(hivReport.getQuarter());
                                    dto.setType("Báo cáo năm");
                                    dto.setReporter(hivReport.getReporter());
                                    dto.setNote((hivReport.getStatus() == null || hivReport.getStatus() != 5) ? "Chưa gửi báo cáo" : "Đã gửi báo cáo");
                                    rs.put(hivReport.getProvince().getId().toString() + "-0", dto);
                                }
                            }
                        }
                    }
                    dashboardNotification.setNotifications(new ArrayList<>(rs.values()));
                }
                return dashboardNotification;
            }
        }
        return null;
    }

    @Override
    public List<NotificationReport> getNotificationReportDistrict(Integer quarter, Integer year, Long adminUnitId) {
        List<Object> listObjects = adminUnitService.getCurrent(adminUnitId);
        if (!CollectionUtils.isEmpty(listObjects)) {
            List<Long> ids = new ArrayList<>();
            HashMap<Long, NotificationReport> rs = new HashMap<>();
            if (listObjects.size() == 1 && listObjects.get(0) instanceof AdminUnitDto) {
                AdminUnitDto adminUnitDto = (AdminUnitDto) listObjects.get(0);
                if (!CollectionUtils.isEmpty(adminUnitDto.getChildren())) {
                    for (Object ob : adminUnitDto.getChildren()) {
                        if (ob instanceof AdminUnitDto) {
                            ids.add(((AdminUnitDto) ob).getId());
                            if (!rs.containsKey(((AdminUnitDto) ob).getId())) {
                                rs.put(((AdminUnitDto) ob).getId(), new NotificationReport(((AdminUnitDto) ob).getId(), ((AdminUnitDto) ob).getName()));
                            }
                        }
                        if (ob instanceof HealthOrgRequest) {
                            ids.add(((HealthOrgRequest) ob).getId());
                            if (!rs.containsKey(((HealthOrgRequest) ob).getId())) {
                                rs.put(((HealthOrgRequest) ob).getId(), new NotificationReport(((HealthOrgRequest) ob).getId(), ((HealthOrgRequest) ob).getName()));
                            }
                        }
                    }
                }
            }
            List<HIVReportRequest> hivReportList = hivReportRepository.findAccountParent(ids, year, quarter);
            if (!CollectionUtils.isEmpty(hivReportList)) {
                for (HIVReportRequest hivReport : hivReportList) {
                    if (hivReport.getCommune() != null && hivReport.getCommune().getId() != null) {
                        if (rs.containsKey(hivReport.getCommune().getId())) {
                            NotificationReport dto = rs.get(hivReport.getCommune().getId());
                            dto.setYear(hivReport.getYear());
                            dto.setQuarter(hivReport.getQuarter());
                            dto.setNote((hivReport.getStatus() == null || hivReport.getStatus() != 5) ? "Chưa gửi báo cáo" : "Đã gửi báo cáo");
                            rs.put(hivReport.getCommune().getId(), dto);
                        }
                    }
                    if (hivReport.getHealthOrg() != null && hivReport.getHealthOrg().getId() != null) {
                        if (rs.containsKey(hivReport.getHealthOrg().getId())) {
                            NotificationReport dto = rs.get(hivReport.getHealthOrg().getId());
                            dto.setYear(hivReport.getYear());
                            dto.setQuarter(hivReport.getQuarter());
                            dto.setNote((hivReport.getStatus() == null || hivReport.getStatus() != 5) ? "Chưa gửi báo cáo" : "Đã gửi báo cáo");
                            rs.put(hivReport.getHealthOrg().getId(), dto);
                        }
                    }
                }
            }
            return new ArrayList<>(rs.values());
        }
        return null;
    }

    @Override
    public List<NotificationReport> getNotificationReportProvince(Integer quarter, Integer year, Long adminUnitId) {
        List<Object> listObjects = adminUnitService.getCurrent(adminUnitId);
        if (!CollectionUtils.isEmpty(listObjects)) {
            List<Long> ids = new ArrayList<>();
            HashMap<Long, NotificationReport> rs = new HashMap<>();
            if (listObjects.size() == 1 && listObjects.get(0) instanceof AdminUnitDto) {
                AdminUnitDto adminUnitDto = (AdminUnitDto) listObjects.get(0);
                if (!CollectionUtils.isEmpty(adminUnitDto.getChildren())) {
                    for (Object ob : adminUnitDto.getChildren()) {
                        if (ob instanceof AdminUnitDto) {
                            ids.add(((AdminUnitDto) ob).getId());
                            if (!rs.containsKey(((AdminUnitDto) ob).getId())) {
                                rs.put(((AdminUnitDto) ob).getId(), new NotificationReport(((AdminUnitDto) ob).getId(), ((AdminUnitDto) ob).getName()));
                            }
                        }
                        if (ob instanceof HealthOrgRequest) {
                            ids.add(((HealthOrgRequest) ob).getId());
                            if (!rs.containsKey(((HealthOrgRequest) ob).getId())) {
                                rs.put(((HealthOrgRequest) ob).getId(), new NotificationReport(((HealthOrgRequest) ob).getId(), ((HealthOrgRequest) ob).getName()));
                            }
                        }
                    }
                }
            }
            List<HIVReportRequest> hivReportList = hivReportRepository.findAccountParent(ids, year, quarter);
            if (!CollectionUtils.isEmpty(hivReportList)) {
                for (HIVReportRequest hivReport : hivReportList) {
                    if (hivReport.getDistrict() != null && hivReport.getDistrict().getId() != null) {
                        if (rs.containsKey(hivReport.getDistrict().getId())) {
                            NotificationReport dto = rs.get(hivReport.getDistrict().getId());
                            dto.setYear(hivReport.getYear());
                            dto.setQuarter(hivReport.getQuarter());
                            dto.setNote((hivReport.getStatus() == null || hivReport.getStatus() != 5) ? "Chưa gửi báo cáo" : "Đã gửi báo cáo");
                            rs.put(hivReport.getDistrict().getId(), dto);
                        }
                    }
                    if (hivReport.getHealthOrg() != null && hivReport.getHealthOrg().getId() != null) {
                        if (rs.containsKey(hivReport.getHealthOrg().getId())) {
                            NotificationReport dto = rs.get(hivReport.getHealthOrg().getId());
                            dto.setYear(hivReport.getYear());
                            dto.setQuarter(hivReport.getQuarter());
                            dto.setNote((hivReport.getStatus() == null || hivReport.getStatus() != 5) ? "Chưa gửi báo cáo" : "Đã gửi báo cáo");
                            rs.put(hivReport.getHealthOrg().getId(), dto);
                        }
                    }
                }
            }
            return new ArrayList<>(rs.values());
        }
        return null;
    }

    private DashboardNotification getTimeDashboard(int accountType) {
        DashboardNotification dashboardNotification = new DashboardNotification();
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int quarter = 0;
        int yearSearch = year;
        int yearQuarter1 = year;
        LocalDate q1District = LocalDate.of(year, Month.MARCH, 18);
        List<CronTaskExpressionRequest> cronTaskQuarterOne = cronTaskExpressionRepository.getListCronTaskExpression(true, accountType, 1);
        if (!CollectionUtils.isEmpty(cronTaskQuarterOne)) {
            CronTaskExpressionRequest item = cronTaskQuarterOne.get(0);
            if (item.getDayOfMonth() != null && item.getMonth() != null && item.getMinusYear() != null) {
                try {
                    q1District = LocalDate.of(year, Integer.parseInt(item.getMonth()), Integer.parseInt(item.getDayOfMonth()));
                    yearQuarter1 += item.getMinusYear();
                } catch (NumberFormatException e) {
                    LOG.error("NumberFormatException at {}", LocalDateTime.now());
                }
            }
        }
        int yearQuarter2 = year;
        LocalDate q2District = LocalDate.of(year, Month.JUNE, 18);
        List<CronTaskExpressionRequest> cronTaskQuarterTwo = cronTaskExpressionRepository.getListCronTaskExpression(true, accountType, 2);
        if (!CollectionUtils.isEmpty(cronTaskQuarterTwo)) {
            CronTaskExpressionRequest item = cronTaskQuarterTwo.get(0);
            if (item.getDayOfMonth() != null && item.getMonth() != null && item.getMinusYear() != null) {
                try {
                    q2District = LocalDate.of(year, Integer.parseInt(item.getMonth()), Integer.parseInt(item.getDayOfMonth()));
                    yearQuarter2 += item.getMinusYear();
                } catch (NumberFormatException e) {
                    LOG.error("NumberFormatException at {}", LocalDateTime.now());
                }
            }
        }
        int yearQuarter3 = year;
        LocalDate q3District = LocalDate.of(year, Month.SEPTEMBER, 18);
        List<CronTaskExpressionRequest> cronTaskQuarterThree = cronTaskExpressionRepository.getListCronTaskExpression(true, accountType, 3);
        if (!CollectionUtils.isEmpty(cronTaskQuarterThree)) {
            CronTaskExpressionRequest item = cronTaskQuarterThree.get(0);
            if (item.getDayOfMonth() != null && item.getMonth() != null && item.getMinusYear() != null) {
                try {
                    q3District = LocalDate.of(year, Integer.parseInt(item.getMonth()), Integer.parseInt(item.getDayOfMonth()));
                    yearQuarter3 += item.getMinusYear();
                } catch (NumberFormatException e) {
                    LOG.error("NumberFormatException at {}", LocalDateTime.now());
                }
            }
        }
        int yearQuarter4 = year;
        LocalDate q4District = LocalDate.of(year, Month.DECEMBER, 18);
        List<CronTaskExpressionRequest> cronTaskQuarterFour = cronTaskExpressionRepository.getListCronTaskExpression(true, accountType, 4);
        if (!CollectionUtils.isEmpty(cronTaskQuarterFour)) {
            CronTaskExpressionRequest item = cronTaskQuarterFour.get(0);
            if (item.getDayOfMonth() != null && item.getMonth() != null && item.getMinusYear() != null) {
                try {
                    q4District = LocalDate.of(year, Integer.parseInt(item.getMonth()), Integer.parseInt(item.getDayOfMonth()));
                    yearQuarter4 += item.getMinusYear();
                } catch (NumberFormatException e) {
                    LOG.error("NumberFormatException at {}", LocalDateTime.now());
                }
            }
        }
        LocalDate yearDistrict = LocalDate.of(year, Month.DECEMBER, 18);
        ;
        List<CronTaskExpressionRequest> cronTaskYearDistrict = cronTaskExpressionRepository.getListCronTaskExpression(null, accountType, null);
        if (!CollectionUtils.isEmpty(cronTaskYearDistrict)) {
            CronTaskExpressionRequest item = cronTaskYearDistrict.get(0);
            if (item.getDayOfMonth() != null && item.getMonth() != null && item.getMinusYear() != null) {
                try {
                    yearDistrict = LocalDate.of(year + item.getMinusYear(), Integer.parseInt(item.getMonth()), Integer.parseInt(item.getDayOfMonth()));
                    yearSearch = year + item.getMinusYear();
                } catch (NumberFormatException e) {
                    LOG.error("NumberFormatException at {}", LocalDateTime.now());
                }
            }
        }
        if (now.isBefore(q1District)) {
            quarter = 4;
            year = yearQuarter1 - 1;
        } else if (now.isBefore(q2District)) {
            quarter = 1;
            year = yearQuarter1;
        } else if (now.isBefore(q3District)) {
            quarter = 2;
            year = yearQuarter2;
        } else if (now.isBefore(q4District)) {
            quarter = 3;
            year = yearQuarter3;
        } else {
            quarter = 4;
            year = yearQuarter4;
        }
        if (now.isBefore(yearDistrict)) {
            yearSearch--;
        }
        dashboardNotification.setYearReportYear(yearSearch);
        dashboardNotification.setQuarterReportQuarter(quarter);
        dashboardNotification.setYearReportQuarter(year);
        return dashboardNotification;
    }

    private List<Object> getCurrent() {
        UserRequest currentUser = userSecurityService.getCurrentUser();
        if (currentUser.getDistrict() != null && currentUser.getDistrict().getId() != null) {
            AdminUnit administrativeUnit = adminUnitRepository.findById(currentUser.getDistrict().getId()).orElse(null);
            if (administrativeUnit != null) {
                AdminUnitDto dto = new AdminUnitDto(administrativeUnit);
                List<HealthOrgRequest> healths = healthOrgRepository.getHealthOrganizationByAdminUnit(dto.getId(), null, dto.getLevel());
                List<Object> healthObject = new ArrayList<>(healths);
                if (dto.getChildren() != null) {
                    dto.getChildren().addAll(healthObject);
                } else {
                    dto.setChildren(healthObject);
                }
                return dto.getChildren();
            }
        }
        if (currentUser.getProvince() != null && currentUser.getProvince().getId() != null) {
            AdminUnit administrativeUnit = adminUnitRepository.findById(currentUser.getProvince().getId()).orElse(null);
            if (administrativeUnit != null) {
                AdminUnitDto dto = new AdminUnitDto(administrativeUnit);
                List<HealthOrgRequest> healths = healthOrgRepository.getHealthOrganizationByAdminUnit(dto.getId(), null, dto.getLevel());
                List<Object> healthObject = new ArrayList<>(healths);
                if (dto.getChildren() != null) {
                    dto.getChildren().addAll(healthObject);
                } else {
                    dto.setChildren(healthObject);
                }
                return dto.getChildren();
            }
        }
        if (currentUser.getAccountType() != null && currentUser.getAccountType().equals(AccountTypeEnum.AREA.getKey())
                && currentUser.getHealthOrganization() != null) {
            List<AdminUnitDto> all = new ArrayList<>();
            if (!CollectionUtils.isEmpty(currentUser.getHealthOrganization().getListAdminUnit())) {
                for (AdminUnitRequest item : currentUser.getHealthOrganization().getListAdminUnit()) {
                    all.add(new AdminUnitDto(item, true));
                }
            }
            return new ArrayList<>(all);
        }
        if (currentUser.getAccountType() != null && currentUser.getAccountType().equals(AccountTypeEnum.VAAC.getKey())) {
            List<AdminUnitDto> all = adminUnitRepository.findAllParentSimple();
            return new ArrayList<>(all);
        }
        return new ArrayList<>();
    }
}
