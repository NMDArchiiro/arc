package com.arc.app.service.report.impl;

import com.arc.app.entity.report.ARVReport;
import com.arc.app.entity.report.ARVReportContent;
import com.arc.app.entity.report.ReportContent;
import com.arc.app.repository.report.ReportContentRepository;
import com.arc.app.request.report.*;
import com.arc.app.service.report.ARVReportService;
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
public class ARVReportServiceImpl implements ARVReportService {
    @Resource
    private ReportContentRepository reportContentRepository;

    @Override
    public ARVReportRequest getARVReport(Long id) {
        ARVReportRequest result = new ARVReportRequest();
        if(id != null) {

        } else {
            List<ReportContent> contents = reportContentRepository.getByReportNumber(ReportEnum.ARV.getNumber());
            result.setContents(new ArrayList<>());
            if (!CollectionUtils.isEmpty(contents)) {
                for (ReportContent item : contents) {
                    ARVReportContentRequest request = new ARVReportContentRequest();
                    request.setReportContent(new ReportContentRequest(item));
                    result.getContents().add(request);
                }
            }
        }
        return result;
    }

    @Override
    public ARVReport setData(ARVReportRequest request) {
        if (request != null) {
            ARVReport entity = new ARVReport();
            if (!CollectionUtils.isEmpty(request.getContents())) {
                Set<ARVReportContent> contents = new HashSet<>();
                for (ARVReportContentRequest item : request.getContents()) {
                    ARVReportContent arvReportContent = new ARVReportContent();
                    arvReportContent.setArvReport(entity);
                    arvReportContent.setContent(reportContentRepository.findById(item.getReportContent().getId()).orElse(null));
                    arvReportContent.setNumberMaleUnder15(item.getNumberMaleUnder15());
                    arvReportContent.setNumberFemaleUnder15(item.getNumberFemaleUnder15());
                    arvReportContent.setNumberTotalUnder15(item.getNumberTotalUnder15());
                    arvReportContent.setNumberMaleOver15(item.getNumberMaleOver15());
                    arvReportContent.setNumberFemaleOver15(item.getNumberFemaleOver15());
                    arvReportContent.setNumberTotalOver15(item.getNumberTotalOver15());
                    arvReportContent.setNumberTotal(item.getNumberTotal());
                    contents.add(arvReportContent);
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
