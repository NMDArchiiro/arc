package com.arc.app.scheduled;

import com.arc.app.repository.report.HIVReportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Calendar;

/**
 * author: NMDuc
 **/
@Component
public class ScheduledReport {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledReport.class);
    @Resource
    private ScheduleHivReport scheduleHivReport;
    @Resource
    private HIVReportRepository hivReportRepository;

    // Tu dong luu cac bao cao xa vao doi moi quy
    @Scheduled(cron = "0 0 0 1 1,4,7,10 *")
    @ConditionalOnExpression("${schedule.auto.create.report.quarter.commune}")
    public void autoCreateReportForCommune() {
        Calendar now = Calendar.getInstance();
        int month = now.get(Calendar.MONTH);
        int quarter = (month / 3) + 1;
        int year = now.get(Calendar.YEAR);
        scheduleHivReport.generateQuarterCommune(quarter, year);
    }

    @Scheduled(cron = "${schedule.auto.lock.report.quarter.commune}")
    public void autoLockQuarterCommune() {
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH);
        int quarter = (month / 3) + 1;
        Integer rs = this.hivReportRepository.lockQuarterCommune(year, quarter);
        LOGGER.info("Locked {} reporting quarter commune and under district at {}", rs, LocalDateTime.now());
    }

    @Scheduled(cron = "${schedule.auto.lock.report.quarter.district}")
    public void autoLockQuarterDistrict() {
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH);
        int quarter = (month / 3) + 1;
        Integer rs = this.hivReportRepository.lockQuarterDistrict(year, quarter);
        LOGGER.info("Locked {} reporting quarter district and under province at {}", rs, LocalDateTime.now());
    }

    @Scheduled(cron = "${schedule.auto.lock.report.quarter.province}")
    public void autoLockQuarterProvince() {
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH);
        int quarter = (month / 3) + 1;
        Integer rs = this.hivReportRepository.lockQuarterProvince(year, quarter);
        LOGGER.info("Locked {} reporting quarter province at {}", rs, LocalDateTime.now());
    }

    @Scheduled(cron = "${schedule.auto.lock.report.year.district}")
    public void autoLockYearDistrict() {
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        Integer rs = this.hivReportRepository.lockYearDistrict(year);
        LOGGER.info("Locked {} reporting year district and under province at {}", rs, LocalDateTime.now());
    }

    @Scheduled(cron = "${schedule.auto.lock.report.year.province}")
    public void autoLockYearProvince() {
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        Integer rs = this.hivReportRepository.lockYearProvince(year);
        LOGGER.info("Locked {} reporting year province at {}", rs, LocalDateTime.now());
    }
}
