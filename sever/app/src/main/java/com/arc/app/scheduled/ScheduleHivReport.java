package com.arc.app.scheduled;

import com.arc.app.entity.base.AdminUnit;
import com.arc.app.entity.report.*;
import com.arc.app.repository.base.AdminUnitRepository;
import com.arc.app.repository.report.HIVReportRepository;
import com.arc.app.repository.report.ReportContentRepository;
import com.arc.app.request.base.AdminUnitRequest;
import com.arc.app.service.base.AdminUnitService;
import com.arc.app.utils.constants.HIVConstants;
import com.arc.app.utils.enums.ReportEnum;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * author: NMDuc
 **/
@Transactional
@Service
public class ScheduleHivReport {
    @Resource
    private AdminUnitRepository adminUnitRepository;
    @Resource
    private ReportContentRepository reportContentRepository;
    @Resource
    private HIVReportRepository hivReportRepository;

    public void generateQuarterCommune(Integer quarter, Integer year) {
        List<AdminUnit> listCommune = adminUnitRepository.getAllCommune(HIVConstants.LEVEL_COMMUNE);
        List<ReportContent> hriContents = reportContentRepository.getByReportNumber(ReportEnum.HRI.getNumber());
        List<ReportContent> athContents = reportContentRepository.getByReportNumber(ReportEnum.ATH.getNumber());
        List<ReportContent> pocContents = reportContentRepository.getByReportNumber(ReportEnum.POC.getNumber());
        if(!CollectionUtils.isEmpty(listCommune) &&  quarter != null && year != null) {
            for(AdminUnit commune : listCommune) {
                HIVReport entity = new HIVReport();
                entity.setQuarter(quarter);
                entity.setYear(year);
                entity.setAdminUnit(commune);
                // Bao cao 1
                HRIReport hriReport = new HRIReport();
                if(!CollectionUtils.isEmpty(hriContents)){
                    Set<HRIReportContent> contents = new HashSet<>();
                    for (ReportContent item : hriContents) {
                        HRIReportContent hriReportContent = new HRIReportContent();
                        hriReportContent.setContent(item);
                        contents.add(hriReportContent);
                    }
                    hriReport.setContents(contents);
                }
                hriReport.setHivReport(entity);
                // Bao cao 2
                ATHReport athReport = new ATHReport();
                if(!CollectionUtils.isEmpty(athContents)){
                    Set<ATHReportContent> contents = new HashSet<>();
                    for (ReportContent item : athContents) {
                        ATHReportContent athContent = new ATHReportContent();
                        athContent.setContent(item);
                        contents.add(athContent);
                    }
                    athReport.setContents(contents);
                }
                athReport.setHivReport(entity);
                // Bao cao 3
                POCReport pocReport = new POCReport();
                if(!CollectionUtils.isEmpty(pocContents)){
                    Set<POCReportContent> contents = new HashSet<>();
                    for (ReportContent item : pocContents) {
                        POCReportContent pocContent = new POCReportContent();
                        pocContent.setContent(item);
                        contents.add(pocContent);
                    }
                    pocReport.setContents(contents);
                }
                pocReport.setHivReport(entity);
                hivReportRepository.save(entity);
            }
        }
    }
}
