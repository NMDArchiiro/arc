package com.arc.app.service.report.impl;

import com.arc.app.entity.report.PREPReport;
import com.arc.app.entity.report.PREPReportContent;
import com.arc.app.entity.report.ReportContent;
import com.arc.app.repository.report.ReportContentRepository;
import com.arc.app.request.report.*;
import com.arc.app.service.report.PREPReportService;
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
public class PREPReportServiceImpl implements PREPReportService {
    @Resource
    private ReportContentRepository reportContentRepository;

    @Override
    public PREPReportRequest getPREPReport(Long id) {
        PREPReportRequest result = new PREPReportRequest();
        if(id != null) {

        } else {
            List<ReportContent> contents = reportContentRepository.getByReportNumber(ReportEnum.PREP.getNumber());
            result.setContents(new ArrayList<>());
            if (!CollectionUtils.isEmpty(contents)) {
                for (ReportContent item : contents) {
                    PREPReportContentRequest request = new PREPReportContentRequest();
                    request.setReportContent(new ReportContentRequest(item));
                    result.getContents().add(request);
                }
            }
        }
        return result;
    }

    @Override
    public PREPReport setData(PREPReportRequest request) {
        if (request != null) {
            PREPReport entity = new PREPReport();
            if (!CollectionUtils.isEmpty(request.getContents())) {
                Set<PREPReportContent> contents = new HashSet<>();
                for (PREPReportContentRequest item : request.getContents()) {
                    PREPReportContent prepReportContent = new PREPReportContent();
                    prepReportContent.setPrepReport(entity);
                    prepReportContent.setContent(reportContentRepository.findById(item.getReportContent().getId()).orElse(null));
                    prepReportContent.setNumberNCMT(item.getNumberNCMT());
                    prepReportContent.setNumberMSM(item.getNumberMSM());
                    prepReportContent.setNumberPNBD(item.getNumberPNBD());
                    prepReportContent.setNumberTG(item.getNumberTG());
                    prepReportContent.setNumberOther(item.getNumberOther());
                    prepReportContent.setNumberTotal(item.getNumberTotal());
                    contents.add(prepReportContent);
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
