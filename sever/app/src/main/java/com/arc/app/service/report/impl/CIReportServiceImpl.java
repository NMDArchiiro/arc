package com.arc.app.service.report.impl;

import com.arc.app.entity.report.CIReport;
import com.arc.app.entity.report.CIReportContent;
import com.arc.app.entity.report.ReportContent;
import com.arc.app.repository.report.ReportContentRepository;
import com.arc.app.request.report.*;
import com.arc.app.service.report.CIReportService;
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
public class CIReportServiceImpl implements CIReportService {
    @Resource
    private ReportContentRepository reportContentRepository;

    @Override
    public CIReportRequest getCIReport(Long id) {
        CIReportRequest result = new CIReportRequest();
        if(id != null) {

        } else {
            List<ReportContent> contents = reportContentRepository.getByReportNumber(ReportEnum.CI.getNumber());
            result.setContents(new ArrayList<>());
            if (!CollectionUtils.isEmpty(contents)) {
                for (ReportContent item : contents) {
                    CIReportContentRequest request = new CIReportContentRequest();
                    request.setReportContent(new ReportContentRequest(item));
                    result.getContents().add(request);
                }
            }
        }
        return result;
    }

    @Override
    public CIReport setData(CIReportRequest request) {
        if (request != null) {
            CIReport entity = new CIReport();
            if (!CollectionUtils.isEmpty(request.getContents())) {
                Set<CIReportContent> contents = new HashSet<>();
                for (CIReportContentRequest item : request.getContents()) {
                    CIReportContent ciReportContent = new CIReportContent();
                    ciReportContent.setCiReport(entity);
                    ciReportContent.setContent(reportContentRepository.findById(item.getReportContent().getId()).orElse(null));
                    ciReportContent.setNumberFemaleOver15(item.getNumberFemaleOver15());
                    ciReportContent.setNumberMaleOver15(item.getNumberMaleOver15());
                    ciReportContent.setNumberTotalOver15(item.getNumberTotalOver15());
                    ciReportContent.setNumberFemaleUnder15(item.getNumberFemaleUnder15());
                    ciReportContent.setNumberMaleUnder15(item.getNumberFemaleUnder15());
                    ciReportContent.setNumberTotalUnder15(item.getNumberTotalUnder15());
                    ciReportContent.setNumberTotal(item.getNumberTotal());
                    contents.add(ciReportContent);
                }
                if (!CollectionUtils.isEmpty(entity.getContents())) {
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
