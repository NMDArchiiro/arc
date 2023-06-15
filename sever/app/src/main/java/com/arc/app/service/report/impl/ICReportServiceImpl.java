package com.arc.app.service.report.impl;

import com.arc.app.entity.report.ICReport;
import com.arc.app.entity.report.ICReportContent;
import com.arc.app.entity.report.ReportContent;
import com.arc.app.repository.report.ReportContentRepository;
import com.arc.app.request.report.ICReportContentRequest;
import com.arc.app.request.report.ICReportRequest;
import com.arc.app.request.report.MDReportContentRequest;
import com.arc.app.request.report.ReportContentRequest;
import com.arc.app.service.report.ICReportService;
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
public class ICReportServiceImpl implements ICReportService {
    @Resource
    private ReportContentRepository reportContentRepository;

    @Override
    public ICReportRequest getICReport(Long id) {
        ICReportRequest result = new ICReportRequest();
        if(id != null) {

        } else {
            List<ReportContent> contents = reportContentRepository.getByReportNumber(ReportEnum.IC.getNumber());
            result.setContents(new ArrayList<>());
            if (!CollectionUtils.isEmpty(contents)) {
                for (ReportContent item : contents) {
                    ICReportContentRequest request = new ICReportContentRequest();
                    request.setReportContent(new ReportContentRequest(item));
                    result.getContents().add(request);
                }
            }
        }
        return result;
    }

    @Override
    public ICReport setData(ICReportRequest request) {
        if (request != null) {
            ICReport entity = new ICReport();
            if (!CollectionUtils.isEmpty(request.getContents())) {
                Set<ICReportContent> contents = new HashSet<ICReportContent>();
                for (ICReportContentRequest item : request.getContents()) {
                    ICReportContent icReportContent = new ICReportContent();
                    icReportContent.setIcReport(entity);
                    icReportContent.setContent(reportContentRepository.findById(item.getReportContent().getId()).orElse(null));
                    icReportContent.setProvision(item.getProvision());
                    icReportContent.setTreatment(item.getTreatment());
                    icReportContent.setLatest(item.getLatest());
                    icReportContent.setReviews(item.getReviews());
                    icReportContent.setCapacityBuilding(item.getCapacityBuilding());
                    contents.add(icReportContent);
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
