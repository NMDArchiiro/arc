package com.arc.app.service.report.impl;

import com.arc.app.entity.report.*;
import com.arc.app.repository.report.ReportContentRepository;
import com.arc.app.request.report.*;
import com.arc.app.service.report.RPREPReportService;
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
public class RPREPReportServiceImpl implements RPREPReportService {
    @Resource
    private ReportContentRepository reportContentRepository;

    @Override
    public RPREPReportRequest getRPREPReport(Long id) {
        RPREPReportRequest result = new RPREPReportRequest();
        if(id != null) {

        } else {
            List<ReportContent> contents = reportContentRepository.getByReportNumber(ReportEnum.RPREP.getNumber());
            result.setContents(new ArrayList<>());
            if (!CollectionUtils.isEmpty(contents)) {
                for (ReportContent item : contents) {
                    RPREPReportContentRequest request = new RPREPReportContentRequest();
                    request.setReportContent(new ReportContentRequest(item));
                    result.getContents().add(request);
                }
            }
        }
        return result;
    }

    @Override
    public RPREPReport setData(RPREPReportRequest request) {
        if (request != null) {
            RPREPReport entity = new RPREPReport();
            if (!CollectionUtils.isEmpty(request.getContents())) {
                Set<RPREPReportContent> contents = new HashSet<>();
                for (RPREPReportContentRequest item : request.getContents()) {
                    RPREPReportContent rprepReportContent = new RPREPReportContent();
                    rprepReportContent.setRprepReport(entity);
                    rprepReportContent.setContent(reportContentRepository.findById(item.getReportContent().getId()).orElse(null));
                    rprepReportContent.setNumberNCMT(item.getNumberNCMT());
                    rprepReportContent.setNumberMSM(item.getNumberMSM());
                    rprepReportContent.setNumberPNBD(item.getNumberPNBD());
                    rprepReportContent.setNumberTG(item.getNumberTG());
                    rprepReportContent.setNumberOther(item.getNumberOther());
                    rprepReportContent.setNumberTotal(item.getNumberTotal());
                    contents.add(rprepReportContent);
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
