package com.arc.app.service.report.impl;

import com.arc.app.entity.report.HIReport;
import com.arc.app.entity.report.HIReportContent;
import com.arc.app.entity.report.ReportContent;
import com.arc.app.repository.report.ReportContentRepository;
import com.arc.app.request.base.HealthOrgRequest;
import com.arc.app.request.report.*;
import com.arc.app.service.report.HIReportService;
import com.arc.app.utils.enums.ReportEnum;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * author: NMDuc
 **/
@Transactional
@Service
public class HIReportServiceImpl implements HIReportService {
    @Resource
    private ReportContentRepository reportContentRepository;

    @Override
    public HIReportRequest getHIReport(Long id) {
        HIReportRequest result = new HIReportRequest();
        if(id != null) {

        } else {
            List<ReportContent> contents = reportContentRepository.getByReportNumber(ReportEnum.HI.getNumber());
            result.setContents(new ArrayList<>());
            if (!CollectionUtils.isEmpty(contents)) {
                for (ReportContent item : contents) {
                    HIReportContentRequest request = new HIReportContentRequest();
                    request.setReportContent(new ReportContentRequest(item));
                    result.getContents().add(request);
                }
            }
        }
        return result;
    }

    @Override
    public HIReport setData(HIReportRequest request) {
        if (request != null) {
            HIReport entity = new HIReport();
            if (!CollectionUtils.isEmpty(request.getContents())) {
                Set<HIReportContent> contents = new HashSet<>();
                for (HIReportContentRequest item : request.getContents()) {
                    HIReportContent hiReportContent = new HIReportContent();
                    hiReportContent.setHiReport(entity);
                    hiReportContent.setContent(reportContentRepository.findById(item.getReportContent().getId()).orElse(null));
                    hiReportContent.setNumberTotalUnder15(item.getNumberTotalUnder15());
                    hiReportContent.setNumberMaleUnder15(item.getNumberMaleUnder15());
                    hiReportContent.setNumberFemaleUnder15(item.getNumberFemaleUnder15());
                    hiReportContent.setNumberTotalOver15(item.getNumberTotalOver15());
                    hiReportContent.setNumberMaleOver15(item.getNumberMaleOver15());
                    hiReportContent.setNumberFemaleOver15(item.getNumberFemaleOver15());
                    hiReportContent.setNumberTotal(item.getNumberTotal());
                    contents.add(hiReportContent);
                }
                if (entity.getContents() != null) {
                    entity.getContents().clear();
                    entity.getContents().addAll(contents);
                } else {
                    entity.setContents(contents);
                }
            }

            return entity;
        }
        return null;
    }
}
