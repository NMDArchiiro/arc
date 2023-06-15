package com.arc.app.service.report.impl;

import com.arc.app.entity.report.CIHCReport;
import com.arc.app.entity.report.CIHCReportContent;
import com.arc.app.entity.report.ReportContent;
import com.arc.app.repository.report.ReportContentRepository;
import com.arc.app.request.report.CIHCReportContentRequest;
import com.arc.app.request.report.CIHCReportRequest;
import com.arc.app.request.report.ReportContentRequest;
import com.arc.app.service.report.CIHCReportService;
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
public class CIHCReportServiceImpl implements CIHCReportService {
    @Resource
    private ReportContentRepository reportContentRepository;

    @Override
    public CIHCReportRequest getCIHCReport(Long id) {
        CIHCReportRequest result = new CIHCReportRequest();
        if(id != null) {

        } else {
            List<ReportContent> contents = reportContentRepository.getByReportNumber(ReportEnum.CIHC.getNumber());
            result.setContents(new ArrayList<>());
            if (!CollectionUtils.isEmpty(contents)) {
                for (ReportContent item : contents) {
                    CIHCReportContentRequest request = new CIHCReportContentRequest();
                    request.setReportContent(new ReportContentRequest(item));
                    result.getContents().add(request);
                }
            }
        }
        return result;
    }

    @Override
    public CIHCReport setData(CIHCReportRequest request) {
        if (request != null) {
            CIHCReport entity = new CIHCReport();
            if (!CollectionUtils.isEmpty(request.getContents())) {
                Set<CIHCReportContent> contents = new HashSet<>();
                for (CIHCReportContentRequest item : request.getContents()) {
                    CIHCReportContent cihcReportContent = new CIHCReportContent();
                    cihcReportContent.setCihcReport(entity);
                    cihcReportContent.setContent(reportContentRepository.findById(item.getReportContent().getId()).orElse(null));
                    cihcReportContent.setNumberFemaleOver15(item.getNumberFemaleOver15());
                    cihcReportContent.setNumberFemaleUnder15(item.getNumberFemaleUnder15());
                    cihcReportContent.setNumberMaleOver15(item.getNumberMaleOver15());
                    cihcReportContent.setNumberMaleUnder15(item.getNumberMaleUnder15());
                    cihcReportContent.setNumberTotalOver15(item.getNumberTotalOver15());
                    cihcReportContent.setNumberTotalUnder15(item.getNumberTotalUnder15());
                    cihcReportContent.setNumberTotal(item.getNumberTotal());
                    contents.add(cihcReportContent);
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
